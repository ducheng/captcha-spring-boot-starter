package com.ducheng.springboot.web;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import com.alibaba.fastjson.JSONObject;
import com.ducheng.springboot.annotation.GuavaCache;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class GuavaCacheReturnValueHandler implements HandlerMethodReturnValueHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GuavaCacheReturnValueHandler.class);
	/**
	 * 这是加载并发的容器
	 */
	private static final ConcurrentMap<String, Cache> GUAVA_CACHE = new ConcurrentHashMap<String, Cache>();
	
	
	/**
	 * 这个注解不能和restcontroller 一起使用
	 */
	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		// 判断是否存在这个注解类型
		return returnType.hasMethodAnnotation(GuavaCache.class);
	}

	@Override
	public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest) throws Exception {
		HttpServletResponse response = (HttpServletResponse) webRequest.getNativeResponse(HttpServletResponse.class);
		Assert.state(response != null, "No HttpServletResponse");
		response.setContentType("application/json;charset=utf-8");
		if (returnType.hasMethodAnnotation(GuavaCache.class)) {
			mavContainer.setRequestHandled(true);
			GuavaCache guavaCache = returnType.getMethodAnnotation(GuavaCache.class);
			Cache cache = CacheBuilder.newBuilder()
		            .concurrencyLevel(guavaCache.concurrencyLevel())
		            .expireAfterWrite(guavaCache.expireTime(), guavaCache.timeUnit())
		            .build();
			if (!StringUtils.isEmpty(GUAVA_CACHE.get(guavaCache.name()))) {
				
				Object ifPresent = GUAVA_CACHE.get(guavaCache.name()).getIfPresent(guavaCache.name());
				if(!StringUtils.isEmpty(ifPresent)) {
					LOGGER.info("开始从缓存中拿数据");
					response.getWriter().write(RemoveEscape(StringEscapeUtils.unescapeJava(JSONObject.toJSONString(ifPresent))));
				}else {
					LOGGER.info("本地缓存失效，在重新放入缓存");
					cache.put(guavaCache.name(), RemoveEscape(StringEscapeUtils.unescapeJava(JSONObject.toJSONString(returnValue))));
					GUAVA_CACHE.put(guavaCache.name(), cache);
					response.getWriter().write(RemoveEscape(StringEscapeUtils.unescapeJava(JSONObject.toJSONString(returnValue))));
				}
			} else {
				LOGGER.info("第一次开始 加入缓存");
				cache.put(guavaCache.name(), RemoveEscape(StringEscapeUtils.unescapeJava(JSONObject.toJSONString(returnValue))));
				GUAVA_CACHE.put(guavaCache.name(), cache);
				response.getWriter().write(RemoveEscape(StringEscapeUtils.unescapeJava(JSONObject.toJSONString(returnValue))));
			}
		}
		response.getWriter().flush();
	}
	
	/**
	 * 这是去掉转义字符
	 * @return
	 */
	public String  RemoveEscape(String value) {
	    String replace = value.replace("\"{", "{");
        String replace2 = replace.replace("}\"", "}");
        String replace3 = replace2.replace("\"[", "[");
        String replace4 = replace3.replace("]\"", "]");
		return replace4;
	}
}
