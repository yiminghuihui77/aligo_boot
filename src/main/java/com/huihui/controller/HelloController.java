package com.huihui.controller;

import com.huihui.annotation.ExtController;
import com.huihui.annotation.ExtRequestMapping;
import org.apache.log4j.Logger;

/**
 * @author minghui.y BG358486
 * @create 2020-10-20 21:57
 **/
@ExtController
@ExtRequestMapping("/hello")
public class HelloController {

    private static final Logger LOGGER = Logger.getLogger(HelloController.class);

    @ExtRequestMapping(value = "/sayHello")
    public String sayHello() {
        LOGGER.info("HelloController.sayHello");
        return "hello world";
    }


}
