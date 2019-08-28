package com.flj.latte.ec.main.personal.settings;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import android.view.View;

import com.alibaba.fastjson.JSON;
import com.diabin.latte.ec.R;
import com.flj.latte.fragments.LatteFragment;
import com.flj.latte.net.RestClient;
import com.flj.latte.net.callback.ISuccess;

/**
 * 关于页
 */
public class AboutFragment extends LatteFragment {

    @Override
    public Object setLayout() {
        return R.layout.delegate_about;
    }

    @Override
    public View setToolbar() {
        return $(R.id.tb_about);
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        final AppCompatTextView textView = $(R.id.tv_info);

        RestClient.builder()
                .url("about.php")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final String info = JSON.parseObject(response).getString("data");
                        textView.setText(info);
                    }
                })
                .build()
                .get();
    }


}
