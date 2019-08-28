package com.latte.fastec;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.flj.latte.fragments.LatteFragment;
import com.flj.latte.net.RestClient;
import com.flj.latte.net.callback.IError;
import com.flj.latte.net.callback.IFailure;
import com.flj.latte.net.callback.ISuccess;

/**
 * 样板页
 */
public class ExampleFragment extends LatteFragment {

    @Override
    public Object setLayout() {
        return R.layout.delegate_example;
    }

    void onClickTest() {
        testWX();

        RestClient.builder()
                .url("index.php")
                .params("key", "value")
                //展示loading,请求结束后自动消失，有可配置样式的重载
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        //请求成功回调
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        //请求失败回调
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        //请求错误回调
                    }
                })
                .build()
                .post();


    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
//        testRestClient();
        $(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickTest();
            }
        });
    }

    private void testWX() {
    }

    @Override
    public View setToolbar() {
        return null;
    }
}
