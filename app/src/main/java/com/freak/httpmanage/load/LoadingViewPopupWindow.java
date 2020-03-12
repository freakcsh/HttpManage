package com.freak.httpmanage.load;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.freak.httpmanage.R;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.core.CenterPopupView;

public class LoadingViewPopupWindow extends CenterPopupView {
    TextView textViewLoadingTitle;
    private Context context;
    private static LoadingViewPopupWindow loadingViewPopupWindow;


    public static LoadingViewPopupWindow getInstance(Context context) {
        if (loadingViewPopupWindow == null) {
            synchronized (LoadingViewPopupWindow.class) {
                if (loadingViewPopupWindow == null) {
                    loadingViewPopupWindow = (LoadingViewPopupWindow) new XPopup.Builder(context)
                            .hasShadowBg(false)
                            .dismissOnTouchOutside(false)
                            .dismissOnBackPressed(true)
                            .autoDismiss(true)
                            .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                            .asCustom(new LoadingViewPopupWindow(context)/*.enableDrag(false)*/);

                }
            }
        }
        return loadingViewPopupWindow;
    }

    public static LoadingViewPopupWindow getInstance(Context context, String title) {
        if (loadingViewPopupWindow == null) {
            synchronized (LoadingViewPopupWindow.class) {
                if (loadingViewPopupWindow == null) {
                    loadingViewPopupWindow = (LoadingViewPopupWindow) new XPopup.Builder(context)
                            .hasShadowBg(false)
                            .dismissOnTouchOutside(false)
                            .dismissOnBackPressed(true)
                            .autoDismiss(true)
                            .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                            .asCustom(new LoadingViewPopupWindow(context)/*.enableDrag(false)*/);
                    loadingViewPopupWindow.setTitle(title);
                }
            }
        }
        return loadingViewPopupWindow;
    }


    public LoadingViewPopupWindow(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.layout_loading_view;
    }

    protected void setup() {
        if (title != null && textViewLoadingTitle != null) {
            textViewLoadingTitle.setVisibility(VISIBLE);
            textViewLoadingTitle.setText(title);
        }
    }


    @Override
    public BasePopupView show() {
        if (loadingViewPopupWindow.isShow()) {
            loadingViewPopupWindow.dismiss();
        }
        return super.show();
    }

    @Override
    public void dismiss() {
        if (loadingViewPopupWindow != null) {
            loadingViewPopupWindow = null;
        }
        super.dismiss();
    }

    @Override
    protected void initPopupContent() {
        super.initPopupContent();
        textViewLoadingTitle = findViewById(R.id.text_view_loading_title);
        setup();
    }

    private String title;

    public LoadingViewPopupWindow setTitle(String title) {
        this.title = title;
        setup();
        return this;
    }

    public void show(String title) {
        setTitle(title);

        this.show();
    }
}
