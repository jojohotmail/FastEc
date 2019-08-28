package com.flj.latte.ec.main.personal.settings;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;

import com.diabin.latte.ec.R;
import com.flj.latte.fragments.LatteFragment;

/**
 * Created by 傅令杰
 */

public class NameFragment extends LatteFragment {

    @Override
    public Object setLayout() {
        return R.layout.delegate_name;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }

    @Override
    public View setToolbar() {
        return null;
    }
}
