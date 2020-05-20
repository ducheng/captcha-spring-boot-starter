# captcha-spring-boot-starter
返回自定义验证码

1. 使用方式maven  依赖 ，已成功发布到maven中央仓库 
<dependency>
   <groupId>com.github.ducheng</groupId>
	   <artifactId>captcha-spring-boot-starter</artifactId>
	  <version>0.0.1</version>
</dependency>

2. 使用测试类 

	@GetMapping("/index")
	@ReturnCaptcha(codeNumber = 6,disturbLinesize = 60)
	public Captcha getindex() {
		return Captcha.LINE;
	}
  
  # 注意不能和@RestController 一起使用
  
  Captcha 是一个枚举，有三种 
  可以 选择验证码的干扰方式 LineCaptcha 线段干扰 (line)
  CircleCaptcha 圆圈干扰(circle)，ShearCaptcha 扭曲干扰(shear)
  对应的四个属性
  // 长、
	int lengSize() default 200;

	// 宽、
	int widhSize() default 100;

	// 验证码字符数、
	int codeNumber() default 4;

	// 干扰线宽度
	int disturbLinesize() default 4;
  
