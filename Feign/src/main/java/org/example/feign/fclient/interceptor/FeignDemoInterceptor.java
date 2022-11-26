package org.example.feign.fclient.interceptor;

import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor(staticName = "of")
public class FeignDemoInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {

        // get 요청일 경우
        if (template.method() == Request.HttpMethod.GET.name()) {
            System.out.println("[GET] [FeignDemoInterceptor] queries : "
                    + template.queries());
            return;
        }

        // post 요청일 경우
        if (template.method() == Request.HttpMethod.POST.name()) {
            String encodedRequestBody = StringUtils.toEncodedString(template.body(), StandardCharsets.UTF_8);
            System.out.println("[POST] [FeignDemoInterceptor] requestBOdy : "
                    + encodedRequestBody);

            String convertRequestBody = encodedRequestBody; // 추가적으로 본인이 필요한 로직 추가
            template.body(convertRequestBody);
            return;
        }
    }
}
