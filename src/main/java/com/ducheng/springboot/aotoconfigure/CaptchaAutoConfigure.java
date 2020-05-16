package com.ducheng.springboot.aotoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ducheng.springboot.web.RetuenCaptchReturnValueHandler;
import com.ducheng.springboot.webmvcconfigure.CaptchaWebMvcConfiguation;

@Configuration
@EnableConfigurationProperties(CaptchaAutoConfigure.class)
@ConfigurationProperties("com.ducheng.captcha")
public class CaptchaAutoConfigure {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RetuenCaptchReturnValueHandler.class);
	
	private boolean enable = true;

	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	@Bean
	public WebMvcConfigurer excelWebMvcConfigurer() {
		CaptchaWebMvcConfiguation configurer = new CaptchaWebMvcConfiguation();
		configurer.setEnable(this.enable);
		LOGGER.info("初始化web 容器");
		return configurer;
	}
}
