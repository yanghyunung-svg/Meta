package com.meta.service;

import com.meta.dto.TbLoginLogDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccessLogService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CommService commService;

    public void logRequest(HttpServletRequest request) {
        if (request.getRequestURI().startsWith("/common")) {
            return;
        }

//        log.debug(BizUtils.logInfo());
        String userId = (String) request.getSession().getAttribute("userId");
        String role = (String) request.getSession().getAttribute("role");
        String loginTime = (String) request.getSession().getAttribute("loginTime");
        String traceId = UUID.randomUUID().toString();
        String query = request.getQueryString();
        request.getSession().setAttribute("traceId", traceId);

        log.info( "○○○○○○○○ [REQ]"
                        + " | " + request.getMethod()
                + " | " + request.getRequestURI()
                + " | user=" + userId + " "
                + " | role=" + role + " "
                + " | query=" + (query  != null ? "?" + query : "")
                   );
        if (request.getMethod().startsWith("GET!")) {
            TbLoginLogDto tbLoginLogDto = new TbLoginLogDto();
            tbLoginLogDto.setUserId(userId);
            tbLoginLogDto.setIpAddr(request.getRequestURI());
            tbLoginLogDto.setUserAgent(traceId  + " | " + loginTime );
            tbLoginLogDto.setLoginResult("0");
            tbLoginLogDto.setFailReason("");

            commService.insertLoginLog(tbLoginLogDto);
        }
    }

    public void logResponse(
            HttpServletRequest request,
            HttpServletResponse response,
            long elapsed,
            Exception ex
    ) {

        if (request.getRequestURI().startsWith("/common")) {
            return;
        }

//        log.debug(BizUtils.logInfo());
        String userId = (String) request.getSession().getAttribute("userId");
        String traceId = (String) request.getSession().getAttribute("traceId");

        log.info( "○○○○○○○○ [RES]"
                + " | " + request.getMethod()
                + " | " + request.getRequestURI()
                + " | user=" + userId + " "
                + " | status=" + response.getStatus()
                + " | time=" + elapsed + "ms"
                + " | " + (ex  != null ? " | error=" + ex.getMessage() : "")
                + " \n."
        );
    }

}
