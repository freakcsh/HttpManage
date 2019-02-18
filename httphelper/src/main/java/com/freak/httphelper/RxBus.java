package com.freak.httphelper;


import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * RxBus event
 * @author freak
 * @date 2019/01/25
 */
public class RxBus {
//    private static volatile RxBus defaultInstance;
////    private final Subject<Object> bus;
//
//    /**
//     * PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
//     */
//    public RxBus() {
////        bus = new SerializedSubject<>(PublishSubject.create());
//    }
//
//    /**
//     * 单例RxBus
//     * @return
//     */
//    public static RxBus getDefault() {
//        if (defaultInstance == null) {
//            synchronized (RxBus.class) {
//                if (defaultInstance == null) {
//                    defaultInstance = new RxBus();
//                }
//            }
//        }
//        return defaultInstance;
//    }
//
//    /**
//     * 发送一个新的事件
//     *
//     * @param o
//     */
//    public void post(Object o) {
//        bus.onNext(o);
//    }
//
//    /**
//     * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
//     * @param eventType
//     * @param <T>
//     * @return
//     */
//    public <T> Observable<T> tObservable(Class<T> eventType) {
//        return bus.ofType(eventType);
//    }
}
