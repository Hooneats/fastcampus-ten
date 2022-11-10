package org.example.mvc.handler.adapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.example.mvc.view.vo.ModelAndView;

public interface HandlerAdapter {

    // 지원하는 핸들러인지
    boolean supports(Object handler);

    // 지원하는 핸들러라면 실행해 모델앤드뷰 리턴
    ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception;
}
