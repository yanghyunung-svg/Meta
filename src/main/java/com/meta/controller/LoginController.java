package com.meta.controller;

import com.common.utils.ApiResponse;
import com.common.utils.BizUtils;
import com.meta.dto.TbLoginLogDto;
import com.meta.dto.TbUserInfoDto;
import com.meta.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
    public ApiResponse<TbUserInfoDto>  getLogin(@RequestBody TbUserInfoDto inputDto, HttpServletRequest request) throws Exception {
        log.debug(BizUtils.logInfo("START"));

        inputDto.setIpAddr(BizUtils.getClientIp(request));
        inputDto.setUserAgent(request.getHeader("User-Agent"));

        ApiResponse<TbUserInfoDto> outputDto = loginService.getLogin(inputDto, request);
        if (!outputDto.isSuccess()) {
            return outputDto;
        }

        log.debug(BizUtils.logInfo("END"));
        return outputDto;
    }


    /**
     * @ ID : getLoginLogList
     * @ NAME : 로그인 로그 조회
     */
    @PostMapping("/getLoginLogList")
    @ResponseBody
    public List<TbLoginLogDto> getLoginLogList(@RequestBody TbLoginLogDto inputDto, HttpServletRequest request) throws Exception {
        log.debug(BizUtils.logInfo("START"));
        log.debug(BizUtils.logVoKey(inputDto));
        return loginService.getLoginLogList(inputDto);
    }


}
