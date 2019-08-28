package com.flj.latte.fragments.bottom;

import android.widget.Toast;

import com.diabin.latte.R;
import com.flj.latte.global.Latte;
import com.flj.latte.fragments.LatteFragment;

/**
 * 控制回退键
 */
public abstract class BottomItemFragment extends LatteFragment {
    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    @Override
    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            _mActivity.finish();
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            Toast.makeText(_mActivity, "双击退出" + Latte.getApplicationContext().getString(R.string.app_name), Toast.LENGTH_SHORT).show();
        }
        return true;
    }



}
