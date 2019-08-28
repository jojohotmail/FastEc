package com.flj.latte.net.interceptors;

import androidx.annotation.NonNull;
import androidx.annotation.RawRes;

import com.flj.latte.global.ConfigKeys;
import com.flj.latte.global.Latte;
import com.flj.latte.util.file.FileUtil;

import java.io.IOException;
import java.util.LinkedHashMap;

import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by 傅令杰 on 2017/4/11
 */

public class DebugInterceptor extends BaseInterceptor {

    private final String DEBUG_URL;
    private final int DEBUG_RAW_ID;

    public DebugInterceptor(String debugUrl, int rawId) {
        this.DEBUG_URL = debugUrl;
        this.DEBUG_RAW_ID = rawId;
    }

    private Response getResponse(Chain chain, String json) {
        return new Response.Builder()
                .code(200)
                .addHeader("Content-Type", "application/json")
                .body(ResponseBody.create(MediaType.parse("application/json"), json))
                .message("OK")
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .build();
    }

    private Response debugResponse(Chain chain, @RawRes int rawId) {
        final String json = FileUtil.getRawFile(rawId);
        return getResponse(chain, json);
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        final String url = chain.request().url().toString();
        final String api = url.substring(url.indexOf("api/") + 4, url.length());
        if (url.contains("user_profile")) {
            if (getBodyParameters(chain) != null && getBodyParameters(chain).size() == 2) {
                final LinkedHashMap<String, String> params = getBodyParameters(chain);

                if (params.get("password") != null && !params.get("password").equals("") && params.get("password").equals("123456")) {
                    if (api.equals(DEBUG_URL)) {
                        return debugResponse(chain, DEBUG_RAW_ID);
                    }
                }
            }
        }


        final boolean answer = (boolean) Latte.getConfiguration(ConfigKeys.IS_CHECK_LOCAL_JSON);
//        if (!answer) {
        if (false) {
            return chain.proceed(chain.request());
        }
        if (api.equals(DEBUG_URL)) {
            return debugResponse(chain, DEBUG_RAW_ID);
        }
        return chain.proceed(chain.request());
    }
}
