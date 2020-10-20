package com.huihui;

import com.huihui.servlet.ExtDispatcherServlet;
import com.huihui.servlet.IndexServlet;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

/**
 * 主类，操作Tomcat容器
 *
 * @author minghui.y BG358486
 * @create 2020-10-19 21:10
 **/
public class TomcatMain {

    private static final int DEFAULT_PORT = 8080;
    public static final String CONTEXT_PATH = "/aligo";

    public static void main(String[] args) throws LifecycleException {
        //创建一个Tomcat服务器
        Tomcat tomcat = new Tomcat();
        //设置端口号
        tomcat.setPort(DEFAULT_PORT);
        tomcat.getConnector().setURIEncoding("UTF-8");
        //非自动部署
        tomcat.getHost().setAutoDeploy(false);

        //创建Context上下文
        StandardContext context = new StandardContext();
        //上下文路径
        context.setPath(CONTEXT_PATH);
        //监听
        context.addLifecycleListener(new Tomcat.FixContextListener());
        //tomcat容器添加上下文
        tomcat.getHost().addChild(context);

        //以下代码类似于在web.xml文件中配置servlet
        tomcat.addServlet(CONTEXT_PATH, "dispatcherServlet", new ExtDispatcherServlet());
        //添加URL映射
        context.addServletMappingDecoded("/", "dispatcherServlet");


        //启动tomcat
        tomcat.start();
        System.out.println("extTomcat已经启动...");
        tomcat.getServer().await();

    }

}
