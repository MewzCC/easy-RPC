package com.cc.protocol;

import org.apache.catalina.*;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;

public class HttpServer {

    public void start(String hostname, Integer port) {
        // 创建一个新的Tomcat实例
        Tomcat tomcat = new Tomcat();

        // 获取Tomcat的Server对象
        Server server = tomcat.getServer();

        // 查找名为"Tomcat"的服务
        Service service = server.findService("Tomcat");

        // 创建并配置Connector
        Connector connector = new Connector();
        connector.setPort(port);

        // 创建并配置Engine
        Engine engine = new StandardEngine();
        engine.setDefaultHost(hostname);

        // 创建并配置Host
        Host host = new StandardHost();
        host.setName(hostname);

        // 设置上下文路径和Context
        String contextPath = "";
        Context context = new StandardContext();
        context.setPath(contextPath);
        context.addLifecycleListener(new Tomcat.FixContextListener());

        // 将Context添加到Host中
        host.addChild(context);

        // 将Host添加到Engine中
        engine.addChild(host);

        // 将Engine添加到Service中
        service.setContainer(engine);

        // 将Connector添加到Service中
        service.addConnector(connector);

        tomcat.addServlet(contextPath,"dispatcher",new DispatcherServlet());
        context.addServletMappingDecoded("/*","dispatcher");

        // 启动Tomcat
        try {
            tomcat.start();
            tomcat.getServer();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    }
