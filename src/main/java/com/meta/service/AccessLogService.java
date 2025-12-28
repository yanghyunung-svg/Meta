package com.meta.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccessLogService {

    public void logRequest(HttpServletRequest request) {
//        if (request.getRequestURI().startsWith("/common")) {
//            return;
//        }

        String userId = (String) request.getSession().getAttribute("userId");
        String traceId = UUID.randomUUID().toString();
        String query = request.getQueryString();
        request.getSession().setAttribute("traceId", traceId);

        System.out.println(traceId + " | [REQ]"
                        + " | " + request.getMethod()
                + " | " + request.getRequestURI()
                + " | user=" + userId + " "
                + " | query=" + (query != null ? "?" + query : "")
                   );
    }

    public void logResponse(
            HttpServletRequest request,
            HttpServletResponse response,
            long elapsed,
            Exception ex
    ) {

//        if (request.getRequestURI().startsWith("/common")) {
//            return;
//        }

        String userId = (String) request.getSession().getAttribute("userId");
        String traceId = (String) request.getSession().getAttribute("traceId");

        System.out.println(traceId + " | [RES]"
                + " | " + request.getMethod()
                + " | " + request.getRequestURI()
                + " | user=" + userId + " "
                + " | status=" + response.getStatus()
                + " | time=" + elapsed + "ms"
                + " | " + (ex != null ? " | error=" + ex.getMessage() : ""));

    }

}
