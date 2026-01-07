package com.meta.service;

import com.meta.common.constants.ResponseCode;
import com.meta.common.exception.BizException;
import com.meta.common.util.BizUtils;
import com.meta.dto.TbLoginLogDto;
import com.meta.dto.TbUserInfoDto;
import com.meta.mapper.TbLoginLogMapper;
import com.meta.mapper.TbUserInfoMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/** 
 *@ ID       : userInfoService
 *@ NAME     : 사용자정보 Service
 */
@Service
public class LoginService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private TbUserInfoMapper tbUserInfoMapper;
    @Autowired
    private TbLoginLogMapper tbLoginLogMapper;
    @Autowired
    private CommService commService;

    /**
     * method   : getLoginData
     * desc     : Login 검증
     */
    public TbUserInfoDto getLogin(TbUserInfoDto inputDto, HttpServletRequest request ) {
        log.debug(BizUtils.logInfo("START"));

        TbLoginLogDto tbLoginLogDto = new TbLoginLogDto();
        tbLoginLogDto.setUserId(inputDto.getUserId());
        tbLoginLogDto.setIpAddr(inputDto.getIpAddr());
        tbLoginLogDto.setUserAgent(inputDto.getUserAgent());
        tbLoginLogDto.setLoginResult("1");

        // 사용자 조회
        TbUserInfoDto outputDto = tbUserInfoMapper.getData(inputDto);
        if (outputDto == null) {
            tbLoginLogDto.setFailReason((ResponseCode.USER_NOT_FOUND.getMessage()));
            commService.insertLoginLog(tbLoginLogDto);
            throw new BizException(ResponseCode.USER_NOT_FOUND);
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = inputDto.getPassword();
        String dbPw = outputDto.getPassword();
        // 비밀번호 검증
        if (!encoder.matches(rawPassword, dbPw)) {
            tbLoginLogDto.setFailReason((ResponseCode.PASSWORD_FAILED.getMessage()));
            commService.insertLoginLog(tbLoginLogDto);
            throw new BizException(ResponseCode.PASSWORD_FAILED);
        }

        // 상태코드 검증
        if (StringUtils.equals(outputDto.getStat(), "0")) {
            tbLoginLogDto.setFailReason((ResponseCode.USER_NO_USED.getMessage()));
            commService.insertLoginLog(tbLoginLogDto);
            throw new BizException(ResponseCode.USER_NO_USED);
        }

        if (StringUtils.equals(outputDto.getStat(), "9")) {
            tbLoginLogDto.setFailReason((ResponseCode.USER_NO_USED.getMessage()));
            commService.insertLoginLog(tbLoginLogDto);
            throw new BizException(ResponseCode.USER_NO_USED);
        }

        tbLoginLogDto.setLoginResult("0");
        tbLoginLogDto.setFailReason("");

        commService.insertLoginLog(tbLoginLogDto);

        HttpSession session = request.getSession(true);
        session.setAttribute("userId", outputDto.getUserId());
        session.setAttribute("userNm", outputDto.getUserNm());
        session.setAttribute("role", outputDto.getRole());
        session.setAttribute("loginTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        log.debug(BizUtils.logInfo("END"));
        return outputDto;

    }

    /**
     * method   : getLoginLogList
     * desc     : 로그인 로그 조회
     */
    public List<TbLoginLogDto> getLoginLogList(TbLoginLogDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        return tbLoginLogMapper.getLoginLogList(inputDto);
    }


}

