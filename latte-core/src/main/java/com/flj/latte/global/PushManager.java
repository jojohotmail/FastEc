package com.flj.latte.global;

import com.flj.latte.util.storage.LattePreference;

/**
 * 消息推送管理
 */
public class PushManager {

    private enum PushTag {
        Push_TAG
    }

    public static void setPushState(boolean state) {
        LattePreference.setAppFlag(PushTag.Push_TAG.name(), state);
    }

    public static boolean isPushState() {
        return LattePreference.getAppFlag(PushTag.Push_TAG.name());
    }


}
