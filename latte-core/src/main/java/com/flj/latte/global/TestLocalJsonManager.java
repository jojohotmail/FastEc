package com.flj.latte.global;

import com.flj.latte.util.storage.LattePreference;

/**
 * Created by 傅令杰 on 2017/4/22
 */

public class TestLocalJsonManager {

    private enum LocalJsonTag {
        LOCAL_JSON_TAG
    }

    //保存使用本地Json数据的状态
    public static void setLocalJsonState(boolean state) {
        LattePreference.setAppFlag(LocalJsonTag.LOCAL_JSON_TAG.name(), state);
    }

    public static boolean isLocalJsonState() {
        return LattePreference.getAppFlag(LocalJsonTag.LOCAL_JSON_TAG.name());
    }


}
