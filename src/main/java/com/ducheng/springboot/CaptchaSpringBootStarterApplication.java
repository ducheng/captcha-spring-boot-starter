package com.ducheng.springboot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ducheng.springboot.annotation.ReturnCaptcha;
import com.ducheng.springboot.enume.Captcha;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.captcha.LineCaptcha;

@SpringBootApplication
@Controller
public class CaptchaSpringBootStarterApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaptchaSpringBootStarterApplication.class, args);
	}
	
	@GetMapping("/index")
	@ReturnCaptcha(codeNumber = 6,disturbLinesize = 60)
	public Captcha getindex() {
		return Captcha.LINE;
	}
}
