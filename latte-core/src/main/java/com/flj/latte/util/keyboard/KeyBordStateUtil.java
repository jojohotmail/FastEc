package com.flj.latte.util.keyboard;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.flj.latte.global.ConfigKeys;
import com.flj.latte.global.Latte;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * @author 顾修忠-guxiuzhong@youku.com/gfj19900401@163.com
 * @Title: KeyBordStateUtil
 * @Package com.youku.crazytogether.app.modules.ugc2.util
 * @Description: 键盘的显示-关闭-以及键盘高度的监听类
 * @date 16/3/6
 * @time 下午6:16
 */
public class KeyBordStateUtil {

    private onKeyBordStateListener listener;
    private View rootLayout;
    private int mVisibleHeight, mFirstVisibleHeight;
    private Activity activity = Latte.getConfiguration(ConfigKeys.ACTIVITY);
    public KeyBordStateUtil(Activity context) {
        rootLayout = ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
        rootLayout.getViewTreeObserver().addOnGlobalLayoutListener(mOnGlobalLayoutListener);
    }

    private ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            calKeyBordState();
        }
    };

    private void calKeyBordState() {
        Rect r = new Rect();
        rootLayout.getWindowVisibleDisplayFrame(r);
        int visibleHeight = r.height();
        if (mVisibleHeight == 0) {
            mVisibleHeight = visibleHeight;
            mFirstVisibleHeight = visibleHeight;
            return;
        }
        if (mVisibleHeight == visibleHeight) {
            return;
        }
        mVisibleHeight = visibleHeight;
        boolean mIsKeyboardShow = mVisibleHeight < mFirstVisibleHeight;
        if (mIsKeyboardShow) {
            int keyboard_height = Math.abs(mVisibleHeight - mFirstVisibleHeight);//键盘高度
            if (listener != null) {
                listener.onSoftKeyBoardShow(keyboard_height);
            }
        } else {
            if (listener != null) {
                listener.onSoftKeyBoardHide();
            }
        }
    }

    public void addOnKeyBordStateListener(onKeyBordStateListener listener) {
        this.listener = listener;
    }

    public void removeOnKeyBordStateListener() {
        if (rootLayout != null && mOnGlobalLayoutListener != null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                rootLayout.getViewTreeObserver().removeGlobalOnLayoutListener(mOnGlobalLayoutListener);
            } else {
                rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(mOnGlobalLayoutListener);
            }
        }
        if (listener != null) {
            listener = null;
        }
    }

    public interface onKeyBordStateListener {
        void onSoftKeyBoardShow(int keyboardHeight);

        void onSoftKeyBoardHide();
    }
    /**
     * 显示键盘
     *
     * @param et 输入焦点
     */
    public void showInput(final EditText et) {
        et.requestFocus();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * 隐藏键盘
     */
    public void hideInput() {

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
        View v = activity.getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}
