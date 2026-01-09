package com.meta.controller;

import com.meta.common.constants.ResponseCode;
import com.meta.common.exception.BizException;
import com.meta.common.response.ApiResponse;
import com.meta.common.util.BizUtils;
import com.meta.dto.TbLoginLogDto;
import com.meta.dto.TbUserInfoDto;
import com.meta.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Controller
@RequestMapping("/meta")
public class LoginController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private LoginService loginService;

    /**
     * @ ID : getLogin
     * @ NAME : Login
     */
    @PostMapping("/getLogin")
    @ResponseBody
    public ApiResponse<TbUserInfoDto> getLogin(@RequestBody TbUserInfoDto inputDto, HttpServletRequest request) {
        log.debug(BizUtils.logInfo("START"));
        inputDto.setIpAddr(BizUtils.getClientIp(request));
        inputDto.setUserAgent(request.getHeader("User-Agent"));

        TbUserInfoDto outputDto = loginService.getLogin(inputDto, request);

        if (outputDto == null) {
            log.debug(BizUtils.logInfo("ERROR"));
            throw new BizException(ResponseCode.PASSWORD_FAILED);
        }

        HttpSession session = request.getSession(true);
        if (session != null) {
            int interval = session.getMaxInactiveInterval();
            String userId = (String) session.getAttribute("userId");
            String role = (String) session.getAttribute("role");
            log.debug(BizUtils.logInfo("interval", String.valueOf(interval)));
            log.debug(BizUtils.logInfo("userId", userId));
            log.debug(BizUtils.logInfo("role", role));
        }

        log.debug(BizUtils.logInfo("END"));
        return ApiResponse.success(outputDto);
    }


    /**
     * @ ID : getLoginLogList
     * @ NAME : 로그인 로그 조회
     */
    @PostMapping("/getLoginLogList")
    @ResponseBody
    public List<TbLoginLogDto> getLoginLogList(@RequestBody TbLoginLogDto inputDto, HttpServletRequest request) throws Exception {
        return loginService.getLoginLogList(inputDto);
    }

    @GetMapping("/remaining-time")
    @ResponseBody
    public Map<String, Object> sessionRemainingTime(HttpSession session) {
        Map<String, Object> result = new HashMap<>();

        if (session == null) {
            result.put("remaining", 0);
            return result;
        }

        int max = session.getMaxInactiveInterval(); // 초
        long lastAccess = session.getLastAccessedTime();
        long now = System.currentTimeMillis();

        long remaining = max - ((now - lastAccess) / 1000);
        result.put("remaining", Math.max(remaining, 0));

        return result;
    }

}
