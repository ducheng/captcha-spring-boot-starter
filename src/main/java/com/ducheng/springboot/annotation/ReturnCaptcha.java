package com.ducheng.springboot.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.LineCaptcha;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ReturnCaptcha {

//	// 这是类型，有三种，默认的是第一种
//	Class<? extends AbstractCaptcha> captchaType() default LineCaptcha.class;

	// 长、
	int lengSize() default 200;

	// 宽、
	int widhSize() default 100;

	// 验证码字符数、
	int codeNumber() default 4;

	// 干扰线宽度
	int disturbLinesize() default 4;
}
