package com.ducheng.springboot.annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GuavaCache {
	
	String name();

	//默认时间
	int expireTime();
	
	//默认单位
	TimeUnit timeUnit();
	
	//并发级别 
	int concurrencyLevel();
	
}
