package com.ducheng.springboot.web;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import com.ducheng.springboot.annotation.ReturnCaptcha;
import com.ducheng.springboot.enume.Captcha;
import com.ducheng.springboot.webmvcconfigure.CaptchaWebMvcConfiguation;

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.ShearCaptcha;
public class RetuenCaptchReturnValueHandler implements HandlerMethodReturnValueHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(RetuenCaptchReturnValueHandler.class);

	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		// 判断是否存在这个注解
		return returnType.hasMethodAnnotation(ReturnCaptcha.class);
	}

	@Override
	public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest) throws Exception {
		/* check */
		HttpServletResponse response = (HttpServletResponse) webRequest.getNativeResponse(HttpServletResponse.class);
		Assert.state(response != null, "No HttpServletResponse");
		ReturnCaptcha returnCaptcha = returnType.getMethodAnnotation(ReturnCaptcha.class);
		Assert.state(returnCaptcha != null, "No @ReturnCaptcha");
		mavContainer.setRequestHandled(true);
		if (!(returnValue instanceof Captcha)) {
			throw new Exception(" 返回类型 不匹配");
		}else { 
			AbstractCaptcha abstractCaptcha = null;
			Captcha captcha =(Captcha)returnValue;
			switch (captcha) {
			case LINE:
				abstractCaptcha = CaptchaUtil.createLineCaptcha(returnCaptcha.lengSize(),
						returnCaptcha.widhSize(),returnCaptcha.codeNumber(),returnCaptcha.disturbLinesize());
				abstractCaptcha.write(response.getOutputStream());
            case SHEAR:
            	abstractCaptcha = CaptchaUtil.createShearCaptcha(returnCaptcha.lengSize(),
						returnCaptcha.widhSize(),returnCaptcha.codeNumber(),returnCaptcha.disturbLinesize());
				abstractCaptcha.write(response.getOutputStream());
			default:
				abstractCaptcha = CaptchaUtil.createCircleCaptcha(returnCaptcha.lengSize(),
						returnCaptcha.widhSize(),returnCaptcha.codeNumber(),returnCaptcha.disturbLinesize());
				abstractCaptcha.write(response.getOutputStream());
			}
		}
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
}
