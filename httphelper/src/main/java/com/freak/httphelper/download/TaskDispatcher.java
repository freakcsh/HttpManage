package com.freak.httphelper.download;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2019/4/9.
 */

public class TaskDispatcher {
    //最大下载任务数量
    private static final int DOWNLOAD_MAX = 3;
    //下载任务线程池
    private ExecutorService executorService;
    //正在下载的任务队列
    private List<DownloadTask> queueTaskList = Collections.synchronizedList(new ArrayList<DownloadTask>());
    //已经完成下载任务队列
    private List<DownloadTask> downloadedList = Collections.synchronizedList(new ArrayList<DownloadTask>());
    //上传任务队列
    private List<DownloadTask> uploadList = Collections.synchronizedList(new ArrayList<DownloadTask>());
    //单例对象
    private static TaskDispatcher instance;
    //任务是否中断
    private boolean taskAbort;

    private TaskDispatcher() {

    }
    /**
     *线程安全单例模式
     */
    public static TaskDispatcher getInstance() {
        if (instance == null) {
            synchronized (TaskDispatcher.class) {
                if (instance == null) {
                    instance = new TaskDispatcher();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化线程池
     */
    private ExecutorService getExecutorService() {
        if (executorService == null) {
            executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                    60, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(),
                    threadFactory(" download dispatcher", false));
        }
        return executorService;
    }

    /**
     * 任务入列下载
     */
    public synchronized boolean enqueue(DownloadTask task) {
        if (queueTaskList.contains(task) || uploadList.contains(task)) {
            return false;
        }
        if (task != null && task.isDownload()) {
            if (queueTaskList.size() < DOWNLOAD_MAX) {
                queueTaskList.add(task);
                getExecutorService().execute(task);
            } else {
                task.setState(DownloadTask.STATE.PENDING);
                queueTaskList.add(task);
            }
            return true;
        } else {
            if (uploadList.size() < DOWNLOAD_MAX) {
                uploadList.add(task);
                getExecutorService().execute(task);
            } else {
                task.setState(DownloadTask.STATE.PENDING);
                uploadList.add(task);
            }
            return true;
        }
    }
    /**
     * 任务下载完成
     */
    public synchronized void finished(DownloadTask task) {
        if (task != null && task.getState() == DownloadTask.STATE.FINISHED) {
            if (task.isDownload()) {
                if (queueTaskList.remove(task)) {
                    downloadedList.add(task);
                    promoteSyncTask();
                }
            } else {
                uploadList.remove(task);
                promoteSyncUploadTask();
            }
        }
    }
    /**
     * 删除下载任务，是否删除文件
     */
    public synchronized void deleteTask(DownloadTask task, boolean isDeleteFile) {
        if (task != null) {
            if (task.getState() != DownloadTask.STATE.FINISHED) {
                if (task.isDownload()) {
                    queueTaskList.remove(task);
                    if (task.getState() == DownloadTask.STATE.LOADING) {
                        task.cancel();
                    }
                    promoteSyncTask();
                } else {
                    uploadList.remove(task);
                    if (task.getState() == DownloadTask.STATE.LOADING) {
                        task.cancel();
                    }
                    promoteSyncUploadTask();
                }
                return;
            }
            downloadedList.remove(task);
            if (isDeleteFile) {
                task.getDownloadFile().delete();
            }
        }
    }
    /**
     *失败任务重新下载
     */
    public synchronized void promoteSyncFailedTask() {
        if (taskAbort && queueTaskList.size() > 0) {
            for (Iterator<DownloadTask> it = queueTaskList.iterator(); it.hasNext(); ) {
                DownloadTask task = it.next();
                if (task.getState() == DownloadTask.STATE.FAILED) {
                    getExecutorService().execute(task);
                }
            }
        }
        if (taskAbort && uploadList.size() > 0) {
            for (Iterator<DownloadTask> it = uploadList.iterator(); it.hasNext(); ) {
                DownloadTask task = it.next();
                if (task.getState() == DownloadTask.STATE.FAILED) {
                    getExecutorService().execute(task);
                }
            }
        }
    }

    /**
     * 调度上传任务
     */
    private synchronized void promoteSyncUploadTask() {
        for (Iterator<DownloadTask> it = uploadList.iterator(); it.hasNext();) {
            DownloadTask task = it.next();
            if (task.getState() == DownloadTask.STATE.PENDING) {
                getExecutorService().execute(task);
                return;
            }
        }
    }

    /**
     * 调度pending状态的任务，开始下载
     */
    private synchronized void promoteSyncTask() {
        for (Iterator<DownloadTask> it = queueTaskList.iterator(); it.hasNext();) {
            DownloadTask task = it.next();
            if (task.getState() == DownloadTask.STATE.PENDING) {
                getExecutorService().execute(task);
                return;
            }
        }
    }

    public List<DownloadTask> getQueueTaskList() {
        return queueTaskList;
    }

    public List<DownloadTask> getDownloadedList() {
        return downloadedList;
    }

    public List<DownloadTask> getUploadList() {
        return uploadList;
    }

    /**
     * 取消所有任务
     */
    public synchronized void cancelAll() {
        for (DownloadTask task : queueTaskList) {
            if (task.getState() == DownloadTask.STATE.LOADING) {
                task.cancel();
            }
        }
        for (DownloadTask task : uploadList) {
            if (task.getState() == DownloadTask.STATE.LOADING) {
                task.cancel();
            }
        }
    }

    public void setTaskAbort(boolean taskAbort) {
        this.taskAbort = taskAbort;
    }

    private ThreadFactory threadFactory(final String name, final boolean daemon) {
        return new ThreadFactory() {
            @Override
            public Thread newThread(@NonNull Runnable r) {
                Thread thread = new Thread(r, name);
                thread.setDaemon(daemon);
                return thread;
            }
        };
    }
}
