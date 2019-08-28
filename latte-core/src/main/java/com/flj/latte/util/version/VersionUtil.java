package com.flj.latte.util.version;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.flj.latte.global.Latte;

public class VersionUtil {
    public static boolean check(final int lineVersion) {
        //当所用app前版本号
        final int nowcodeversin = getVersion();
        if (lineVersion > nowcodeversin) {
            return true;
        }

        return false;
    }
    public static int getVersion() {
        PackageInfo pkg;
        int versionCode = 0;
        String versionName = "";
        try {
            pkg = Latte.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(Latte.getApplicationContext().getPackageName(), 0);
            versionCode = pkg.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        return versionCode;
    }
}
