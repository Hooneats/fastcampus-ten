package org.example;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.example.servlet.CalculatorServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class WebApplicationServerLauncher {
    private static final Logger logger = LoggerFactory.getLogger(WebApplicationServerLauncher.class);

    public static void main(String[] args) throws Exception {
        // 내장 톰캣
        String webappDirLocation = "servlet/webapp/"; // classpath fast-ten/~~~
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        Connector connector = tomcat.getConnector();
        connector.setURIEncoding("UTF-8");
        tomcat.setConnector(connector);

        CalculatorServlet calculatorServlet = new CalculatorServlet();
        calculatorServlet.init();

        tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());
        logger.info("configuring app with basedir: {}", new File("./" + webappDirLocation).getAbsolutePath());

        tomcat.start();
        tomcat.getServer().await();
    }
}
