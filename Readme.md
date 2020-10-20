aligo-boot
==========

这个项目用于学习SprigBoot启动原理
涉及知识点：
1、操作tomcat api
2、手写Spring IOC容器
3、手写SpringMVC，拦截路径：/   

###思路：
>>手动创建tomcat服务器，配置基础信息，如端口，上下文路径等

>>手写一个DispatcherServlet（HttpServlet）：
在init()方法中初始化IOC容器和mvc
在doPost()方法中，获取请求url，根据url在mvc上下文中获取目标执行方法Method和ioc中的bean
反射执行Method，获取方法返回值，写入响应

>>将上述手写的DispatcherServlet注册到tomcat服务器中