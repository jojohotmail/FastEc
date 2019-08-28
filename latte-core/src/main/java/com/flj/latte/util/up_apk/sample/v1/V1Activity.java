package com.flj.latte.util.up_apk.sample.v1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.allenliu.versionchecklib.core.AllenChecker;
import com.allenliu.versionchecklib.core.VersionDialogActivity;
import com.allenliu.versionchecklib.core.VersionParams;
import com.diabin.latte.R;

public class V1Activity extends AppCompatActivity {
    private EditText etPauseTime;
    private EditText etAddress;
    private RadioGroup radioGroup;
    private CheckBox forceUpdateCheckBox;
    private CheckBox silentDownloadCheckBox;
    private CheckBox forceDownloadCheckBox;
    private CheckBox onlyDownloadCheckBox;
    private CheckBox showNotificationCheckBox;
    private CheckBox showDownloadingCheckBox;
    private RadioGroup radioGroup2;

    public static V1Activity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cv_activity_v1);
        etPauseTime = (EditText) findViewById(R.id.etTime);
        etAddress = (EditText) findViewById(R.id.etAddress);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup2 = (RadioGroup) findViewById(R.id.radioGroup2);
        silentDownloadCheckBox = (CheckBox) findViewById(R.id.checkbox2);
        forceUpdateCheckBox = (CheckBox) findViewById(R.id.checkbox);
        forceDownloadCheckBox = (CheckBox) findViewById(R.id.checkbox3);
        onlyDownloadCheckBox = (CheckBox) findViewById(R.id.checkbox4);
        showNotificationCheckBox = (CheckBox) findViewById(R.id.checkbox5);
        showDownloadingCheckBox = (CheckBox) findViewById(R.id.checkbox6);
        mainActivity = this;


    }

    public void onClick(View view) {
        //you can add your request params and request method
        //eg.
        //只有requsetUrl service 是必须值 其他参数都有默认值，可选

//        com.allenliu.versionchecklib.core.http.HttpHeaders headers=new com.allenliu.versionchecklib.core.http.HttpHeaders();
//        headers.put("a","b");
        VersionParams.Builder builder = new VersionParams.Builder()
//                .setHttpHeaders(headers)
//                .setRequestMethod(requestMethod)
//                .setRequestParams(httpParams)
                .setRequestUrl("https://www.baidu.com")
//                .setDownloadAPKPath(getApplicationContext().getFilesDir()+"/")
                .setService(DemoService.class);

        stopService(new Intent(this, DemoService.class));
        if (view.getId() == R.id.sendbtn) {
            String pauseTime = etPauseTime.getText().toString();
            String address = etAddress.getText().toString();
            try {
                if (!pauseTime.isEmpty() && Long.valueOf(pauseTime) > 0) {
                    builder.setPauseRequestTime(Long.valueOf(pauseTime));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!address.isEmpty())
                builder.setDownloadAPKPath(address);
            //更新界面选择
            if (radioGroup.getCheckedRadioButtonId() == R.id.btn1) {
                CustomVersionDialogActivity.customVersionDialogIndex = 3;
                builder.setCustomDownloadActivityClass(VersionDialogActivity.class);
            }
             else if(radioGroup.getCheckedRadioButtonId() == R.id.btn2) {
                CustomVersionDialogActivity.customVersionDialogIndex = 1;
                builder.setCustomDownloadActivityClass(CustomVersionDialogActivity.class);
            }
            else if (radioGroup.getCheckedRadioButtonId() == R.id.btn3) {
                CustomVersionDialogActivity.customVersionDialogIndex = 2;
                builder.setCustomDownloadActivityClass(CustomVersionDialogActivity.class);
            }
            //下载进度界面选择
            if (radioGroup2.getCheckedRadioButtonId()==R.id.btn21){
                //同理
                CustomVersionDialogActivity.isCustomDownloading = false;
                builder.setCustomDownloadActivityClass(VersionDialogActivity.class);
            } else if(radioGroup2.getCheckedRadioButtonId()==R.id.btn22){
                //可以看到 更改更新界面或者是更改下载界面都是重写VersionDialogActivity
                CustomVersionDialogActivity.isCustomDownloading = true;
                builder.setCustomDownloadActivityClass(CustomVersionDialogActivity.class);
            }

            //强制更新
            if (forceUpdateCheckBox.isChecked()) {
                CustomVersionDialogActivity.isForceUpdate = true;
                builder.setCustomDownloadActivityClass(CustomVersionDialogActivity.class);
            } else {
                //同理
                CustomVersionDialogActivity.isForceUpdate = false;
                builder.setCustomDownloadActivityClass(CustomVersionDialogActivity.class);
            }
            //静默下载
            if (silentDownloadCheckBox.isChecked()) {
                builder.setSilentDownload(true);
            } else {
                builder.setSilentDownload(false);
            }
            //强制重新下载
            if (forceDownloadCheckBox.isChecked()) {
                builder.setForceRedownload(true);
            } else {
                builder.setForceRedownload(false);
            }
            //是否仅使用下载功能
            if (onlyDownloadCheckBox.isChecked()) {
                //如果仅使用下载功能，downloadUrl是必须的
                builder.setOnlyDownload(true)
                        .setDownloadUrl("http://test-1251233192.coscd.myqcloud.com/1_1.apk")
                        .setTitle("检测到新版本")
                        .setUpdateMsg(getString(R.string.updatecontent));
            } else
                builder.setOnlyDownload(false);
            //是否显示通知栏
            if (showNotificationCheckBox.isChecked()) {
                builder.setShowNotification(true);
            } else
                builder.setShowNotification(false);
            if (showDownloadingCheckBox.isChecked()) {
                builder.setShowDownloadingDialog(true);
            } else
                builder.setShowDownloadingDialog(false);

            builder.setShowDownLoadFailDialog(false);
//                builder.setDownloadAPKPath("/storage/emulated/0/AllenVersionPath2/");
            AllenChecker.startVersionCheck(getApplication(), builder.build());

        } if (view.getId() == R.id.cancelBtn) {
            AllenChecker.cancelMission();
//                VersionParams.Builder builder2 = new VersionParams.Builder();
//                builder2.setOnlyDownload(true)
//                        .setDownloadUrl("http://test-1251233192.coscd.myqcloud.com/1_1.apk")
//                        .setTitle("检测到新版本")
//                        .setForceRedownload(true)
//                        .setUpdateMsg(getString(R.string.updatecontent));
//                AllenChecker.startVersionCheck(this, builder2.build());
        }


    }
}
