package com.ducheng.springboot.aotoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.ducheng.springboot.webmvcconfigure.GuavaCacheWebMvcConfiguation;


@Configuration
@EnableConfigurationProperties(GuavaCacheConfigure.class)
@ConfigurationProperties("com.ducheng.guava.cache")
public class GuavaCacheConfigure {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GuavaCacheConfigure.class);
	
	private boolean enable = true;

	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	@Bean
	public WebMvcConfigurer excelWebMvcConfigurer() {
		GuavaCacheWebMvcConfiguation configurer = new GuavaCacheWebMvcConfiguation();
		configurer.setEnable(this.enable);
		LOGGER.info("start guavacache ");
		return configurer;
	}
}