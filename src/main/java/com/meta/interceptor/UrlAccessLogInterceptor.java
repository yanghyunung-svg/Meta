package com.meta.interceptor;

import com.meta.service.AccessLogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class UrlAccessLogInterceptor implements HandlerInterceptor {

    private final AccessLogService accessLogService;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) {
        // 시작 시간
        request.setAttribute("startTime", System.currentTimeMillis());

        accessLogService.logRequest(request);
        return true;
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex
    ) {
        request.setAttribute("endTime", System.currentTimeMillis());

        long startTime = (long) request.getAttribute("startTime");
        long elapsed = System.currentTimeMillis() - startTime;

        accessLogService.logResponse(request, response, elapsed, ex);
    }
}

