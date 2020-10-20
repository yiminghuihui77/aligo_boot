package com.huihui.servlet;

import com.huihui.TomcatMain;
import com.huihui.annotation.ExtController;
import com.huihui.annotation.ExtRequestMapping;
import com.huihui.utils.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/**
 * 自定义DispatcherServlet
 *
 * @author minghui.y BG358486
 * @create 2020-10-20 21:42
 **/
public class ExtDispatcherServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ExtDispatcherServlet.class);

    private static final String COMPONENT_SCAN_PATH = "com.huihui";

    private final Map<String, Object> beanMap = new ConcurrentHashMap<>();
    private final Map<String, Object> urlBeanMap = new ConcurrentHashMap<>();
    private final Map<String, Method> urlMethod = new ConcurrentHashMap<>();

    @Override
    public void init() throws ServletException {
        LOGGER.info("ExtDispatcherServlet init ...");

        //初始化IOC容器
        initIoc();

        //初始化mvc
        initMvc();

    }

    /**
     * 初始化mvc
     */
    private void initMvc() {
        //对ioc容器中的bean
        for (Object bean : beanMap.values()) {
            //url路径
            String baseUrl = TomcatMain.CONTEXT_PATH;

            Class<?> clazz = bean.getClass();
            ExtRequestMapping extRequestMapping = clazz.getAnnotation(ExtRequestMapping.class);
            if (extRequestMapping != null) {
                baseUrl += extRequestMapping.value();
            }

            //获取类中所有方法
            for (Method method : clazz.getMethods()) {
                ExtRequestMapping methodMapping = method.getAnnotation(ExtRequestMapping.class);
                if (methodMapping != null) {
                    String completeUrl = baseUrl + methodMapping.value();
                    //存储url与bean和method的关系
                    urlBeanMap.put(completeUrl, bean);
                    urlMethod.put(completeUrl, method);
                }

            }

        }

    }



    /**
     * 初始化IOC容器
     */
    private void initIoc() {
        //扫包获取所以Class
        List<Class<?>> classList = ClassUtils.getClasses(COMPONENT_SCAN_PATH);

        for (Class<?> clazz : classList) {
            //判断类上的注解
            ExtController extController = clazz.getAnnotation(ExtController.class);
            if (extController == null) {
                continue;
            }
            //默认类名(首字母小写)作为beanId
            String beanId = ClassUtils.generateBeanId(clazz);
            try {
                beanMap.put(beanId, clazz.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        //TODO 依赖注入


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //获取请求url
        String url = req.getRequestURI();

        //获取
        Object bean = urlBeanMap.get(url);
        Method method = urlMethod.get(url);

        //反射调用
        try {
            String result = (String) method.invoke(bean);
            resp.getWriter().println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    public void destroy() {
        LOGGER.info("ExtDispatcherServlet destroy...");
    }
}
