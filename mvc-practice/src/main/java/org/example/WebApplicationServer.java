package org.example;

import java.io.File;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

@Slf4j
public class WebApplicationServer {

    /**
     * 톰켓의 규약 : 루트 디렉토리 밑에 /WEB-INF/classes 밑에서 필요한 파일을 찾게된다.
     *  ㄴ 컴파일을 인텔리제이로바꾸고 빌드 아웃풋을(프로젝트구조에서 mvc-practice 의 main 부분)
     *  /Users/study/fast-ten/mvc-practice/webapps/WEB-INF/classes 로 변경
     *
     * @param args
     * @throws LifecycleException
     */
    public static void main(String[] args) throws LifecycleException {
        // 루트 디렉토리 설정
        String webapplication = "mvc-practice/webapps/";
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);

        tomcat.addWebapp("/", new File(webapplication).getAbsolutePath());
        log.info("configuring app with baseDir: {}",
            new File("/" + webapplication).getAbsolutePath());

        tomcat.start();
        tomcat.getServer().await();
    }
}
