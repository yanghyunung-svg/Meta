package com.meta.controller;

import com.common.utils.ApiResponse;
import com.common.utils.BizUtils;
import com.meta.dto.TbLoginLogDto;
import com.meta.dto.TbUserInfoDto;
import com.meta.service.UserInfoService;
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
public class UserInfoController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserInfoService userInfoService;

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

        ApiResponse<TbUserInfoDto> outputDto = userInfoService.getLogin(inputDto, request);
        if (!outputDto.isSuccess()) {
            return outputDto;
        }

        log.debug(BizUtils.logInfo("END"));
        return outputDto;
    }

    /**
     * @ ID : getUserListData
     * @ NAME : 사용자정보 목록 조회
     */
    @PostMapping("/getUserListData")
    @ResponseBody
    public List<TbUserInfoDto> getUserListData(@RequestBody TbUserInfoDto inputDto, HttpServletRequest request) throws Exception {
        log.debug(BizUtils.logInfo("START"));
        return userInfoService.getListData(inputDto);
    }

    /**
     * @ ID : getUserData
     * @ NAME : 사용자정보 상세 조회
     */
    @PostMapping("/getUserData")
    @ResponseBody
    public TbUserInfoDto getUserData(@RequestBody TbUserInfoDto inputDto, HttpServletRequest request) throws Exception {
        log.debug(BizUtils.logInfo("START"));
        return userInfoService.getData(inputDto);
    }

    /**
     * @ ID : insertUserData
     * @ NAME : 사용자정보 등록
     */
    @PostMapping("/insertUserData")
    @ResponseBody
    public ApiResponse<Void> insertUserData(@RequestBody TbUserInfoDto inputDto, HttpServletRequest request) throws Exception {
        log.debug(BizUtils.logInfo("START", BizUtils.logVoKey(inputDto)));
        ApiResponse<Void> outputDto = userInfoService.insertData(inputDto);
        log.debug(BizUtils.logInfo("END", BizUtils.logVo(outputDto)));
        return outputDto;
    }
    /**
     * @ ID : changePassword
     * @ NAME : 비밀번호 변경
     */
    @PostMapping("/changePassword")
    @ResponseBody
    public ApiResponse<Void> changePassword(@RequestBody TbUserInfoDto inputDto, HttpServletRequest request) throws Exception {
        log.debug(BizUtils.logInfo("START", BizUtils.logVoKey(inputDto)));
        ApiResponse<Void> outputDto = userInfoService.changePassword(inputDto);
        log.debug(BizUtils.logInfo("END", BizUtils.logVo(outputDto)));
        return outputDto;
    }
    /**
     * @ ID : updateUserData
     * @ NAME : 사용자정보 변경
     */
    @PostMapping("/updateUserData")
    @ResponseBody
    public ApiResponse<Void> updateUserData(@RequestBody TbUserInfoDto inputDto, HttpServletRequest request) throws Exception {
        log.debug(BizUtils.logInfo("START", BizUtils.logVoKey(inputDto)));
        ApiResponse<Void> outputDto = userInfoService.updateData(inputDto);
        log.debug(BizUtils.logInfo("END", BizUtils.logVo(outputDto)));
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
        return userInfoService.getLoginLogList(inputDto);
    }
}
