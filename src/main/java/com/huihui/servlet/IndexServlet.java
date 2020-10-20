package com.huihui.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 第一个Servlet
 *
 * @author minghui.y BG358486
 * @create 2020-10-19 21:07
 **/
public class IndexServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("IndexServlet start to handle request...");

        resp.setCharacterEncoding("GBK");
        resp.getWriter().println("我是IndexServlet！");


        System.out.println("IndexServlet start to handle request...");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
