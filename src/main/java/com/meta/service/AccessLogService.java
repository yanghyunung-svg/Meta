package com.meta.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class AccessLogService {

    public void logRequest(HttpServletRequest request) {
        if (!request.getRequestURI().startsWith("/meta")) {
            return;
        }

        String url = request.getRequestURI();
        String method = request.getMethod();
        String query = request.getQueryString();
        String ip = getClientIp(request);
        String userId = (String) request.getSession().getAttribute("userId");
        String startTime = (String) request.getSession().getAttribute("startTime");

        System.out.println("[REQ] " +
                url + " " +  startTime + " " +
                " | user=" + userId + " " + method + " " +
                " | ip=" + ip + " " +
                (query != null ? "?" + query : "") );
    }

    public void logResponse(
            HttpServletRequest request,
            HttpServletResponse response,
            long elapsed,
            Exception ex
    ) {

        if (!request.getRequestURI().startsWith("/meta")) {
            return;
        }

        String userId = (String) request.getSession().getAttribute("userId");
        String endTime = (String) request.getSession().getAttribute("endTime");

        System.out.println("[RES] " +
                request.getRequestURI() + " " +  endTime + " " +
                " | user=" + userId +
                " | status=" + response.getStatus() +
                " | time=" + elapsed + "ms" +
                (ex != null ? " | error=" + ex.getMessage() : ""));
    }

    public static String getClientIp(HttpServletRequest request) {
        String[] headerKeys = {
                "X-Forwarded-For",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_CLIENT_IP",
                "HTTP_X_FORWARDED_FOR",
                "X-Real-IP"
        };

        for (String header : headerKeys) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() > 0 && !"unknown".equalsIgnoreCase(ip)) {
                // 다중 프록시 대응
                return ip.split(",")[0].trim();
            }
        }

        String ip = request.getRemoteAddr();

        // IPv6 localhost 처리
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }

        return ip;
    }

}
