package org.example;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.example.servlet.CalculatorServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class WebApplicationServerLauncher {
    private static final Logger logger = LoggerFactory.getLogger(WebApplicationServerLauncher.class);

    /**
     * 톰켓의 규약 : 루트 디렉토리 밑에 /WEB-INF/classes 밑에서 필요한 파일을 찾게된다.
     *  ㄴ 컴파일을 인텔리제이로바꾸고 빌드 아웃풋을(프로젝트구조에서 mvc-practice 의 main 부분)
     *  /Users/study/fast-ten/servlet/webapps/WEB-INF/classes 로 변경
     *
     * @param args
     * @throws Exception
     */
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
