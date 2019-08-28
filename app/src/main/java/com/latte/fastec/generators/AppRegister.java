package com.latte.fastec.generators;

import com.flj.latte.annotations.AppRegisterGenerator;
import com.flj.latte.wechat.templates.AppRegisterTemplate;

/**
 * Created by 傅令杰 on 2017/4/22
 */
@SuppressWarnings("unused")
@AppRegisterGenerator(
        packageName = "com.latte.fastec",
        registerTemplate = AppRegisterTemplate.class
)
public interface AppRegister {
}
