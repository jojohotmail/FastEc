package com.latte.fastec;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import com.flj.latte.activities.ProxyActivity;
import com.flj.latte.ec.launcher.LauncherFragment;
import com.flj.latte.ec.main.EcBottomFragment;
import com.flj.latte.ec.sign.ISignListener;
import com.flj.latte.ec.sign.SignInFragment;
import com.flj.latte.fragments.LatteFragment;
import com.flj.latte.global.Latte;
import com.flj.latte.global.TestLocalJsonManager;
import com.flj.latte.ui.launcher.ILauncherListener;
import com.flj.latte.ui.launcher.OnLauncherFinishTag;

//import cn.jpush.android.api.JPushInterface;
import qiu.niorgai.StatusBarCompat;

public class ExampleActivity extends ProxyActivity implements
        ISignListener,
        ILauncherListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        Latte.getConfigurator().withActivity(this);
        StatusBarCompat.translucentStatusBar(this, true);
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        JPushInterface.onPause(this);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        JPushInterface.onResume(this);
//    }

    @Override
    public LatteFragment setRootDelegate() {
        return new EcBottomFragment();
//        return new LauncherFragment();
    }

    @Override
    public void onSignInSuccess() {
        Toast.makeText(this, "登录成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSignUpSuccess() {
        Toast.makeText(this, "注册成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLauncherFinish(OnLauncherFinishTag tag) {
        switch (tag) {
            case SIGNED:
//                Toast.makeText(this, "启动结束，用户登录了", Toast.LENGTH_LONG).show();
                getSupportDelegate().startWithPop(new EcBottomFragment());
                break;
            case NOT_SIGNED:
//                Toast.makeText(this, "启动结束，用户没登录", Toast.LENGTH_LONG).show();
                getSupportDelegate().startWithPop(new SignInFragment());
                break;
            default:
                break;
        }
    }

    @Override
    public void post(Runnable runnable) {

    }
}
