package com.flj.latte.util.up_apk.sample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.diabin.latte.R;
import com.flj.latte.util.up_apk.sample.v1.V1Activity;
import com.flj.latte.util.up_apk.sample.v2.V2Activity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cv_activity_main);
    }

    public void mainOnClick(View view) {
        Intent intent;
        if (view.getId()==R.id.btn_v1){
            intent = new Intent(this, V1Activity.class);
            startActivity(intent);
        }else if (view.getId()==R.id.btn_v2){
            intent = new Intent(this, V2Activity.class);
            startActivity(intent);
        }

    }
}
