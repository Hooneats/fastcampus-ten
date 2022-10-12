package org.example;

import org.example.calculator.Calculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Step1 - 사용자 요청을 메인 thread 가 처리하도록 한다.
 * HttpRequest
 * ㄴ Header  -> GET /calculate?operand1=11&operator=*&operand2=55 HTTP/1.1
 ㄴ HttpMethod , path , queryString , protocol/version
 ㄴ ...
 * ㄴ Blank Line
 * ㄴ Body
 *
 * HttpResponse
 * ㄴ Header
 * ㄴ Blank Line
 * ㄴ Body
 */
public class ClientRequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ClientRequestHandler.class);

    private final Socket clientSocket;

    public ClientRequestHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        logger.info("[ClientRequestHandler] new Client {} started.", Thread.currentThread().getName());

        try (
                InputStream in = clientSocket.getInputStream();
                OutputStream out = clientSocket.getOutputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
                DataOutputStream dos = new DataOutputStream(out)
        ) {

            HttpRequest httpRequest = new HttpRequest(br);

            // GET /calculate?operand1=11&operator=*&operand2=55 HTTP/1.1
            if (httpRequest.isGetRequest() && httpRequest.matchPath("/calculate")) {
                QueryStrings queryStrings = httpRequest.getQueryString();

                int operand1 = Integer.parseInt(queryStrings.getValue("operand1"));
                String operator = queryStrings.getValue("operator");
                int operand2 = Integer.parseInt(queryStrings.getValue("operand2"));

                int result = Calculator.calculate(operand1, operator, operand2);
                byte[] body = String.valueOf(result).getBytes();

                HttpResponse httpResponse = new HttpResponse(dos);
                httpResponse.response200Header("application/json", body.length);
                httpResponse.responseBody(body);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
