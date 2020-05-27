package com.ducheng.springboot.webmvcconfigure;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ducheng.springboot.web.GuavaCacheReturnValueHandler;
public class GuavaCacheWebMvcConfiguation implements WebMvcConfigurer {

	private static final Logger LOGGER = LoggerFactory.getLogger(GuavaCacheWebMvcConfiguation.class);

	private boolean enable = true;

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	
	@Override
	public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
		if (this.enable) {
			LOGGER.info(" 注入guavacache web 容器成功 ");
			handlers.add(new GuavaCacheReturnValueHandler());
		}
	}
}
