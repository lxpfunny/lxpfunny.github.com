---
layout: post
title: 在Spring中获取ServletContext方法
---
<p> 
注意：下面代码是在web环境中才能获取到WebApplicationContext,ContextLoader为spring中创建applicaitonContext上下文的类，而applicationContext是spring的核心
</p>

<p>
	

	WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();   
    ServletContext servletContext = webApplicationContext.getServletContext(); 
</p>



 