package com.meta.service;

import ch.qos.logback.core.util.StringUtil;
import com.common.utils.ApiResponse;
import com.common.utils.BizUtils;
import com.meta.dto.TbLoginLogDto;
import com.meta.dto.TbUserInfoDto;
import com.meta.mapper.TbLoginLogMapper;
import com.meta.mapper.TbUserInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.List;

/** 
 *@ID       : userInfoService
 *@NAME     : 사용자정보 Service
 */
@Service
public class UserInfoService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private TbUserInfoMapper tbUserInfoMapper;
    @Autowired
    private TbLoginLogMapper tbLoginLogMapper;

    /**
     * method   : getListData
     * desc     : 사용자정보 목록 조회
     */
    public List<TbUserInfoDto> getListData(TbUserInfoDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        return tbUserInfoMapper.getListData(inputDto);
    }

    /**
     * method   : getData
     * desc     : 사용자정보 상세 조회
     */
    public TbUserInfoDto getData(TbUserInfoDto inputDto)  {
        log.debug(BizUtils.logInfo("START", BizUtils.logVoKey(inputDto)));
        TbUserInfoDto outputDto = tbUserInfoMapper.getData(inputDto);
        log.debug(BizUtils.logInfo("END", BizUtils.logVo(outputDto)));
        return outputDto;
    }
    /**
     * method   : insertData
     * desc     : 사용자정보 등록
     */
    public ApiResponse<Void> insertData(TbUserInfoDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        try {
            TbUserInfoDto outputDto = tbUserInfoMapper.getData(inputDto);

            if (outputDto != null) {
                return new ApiResponse<Void>(false, "기등록된 데이타가 있습니다." + outputDto.getUserNm());
            }

            if(!StringUtil.isNullOrEmpty(inputDto.getPassword())) {
                String rawPassword = inputDto.getPassword();
                String encodedPassword = encoder.encode(rawPassword);
                inputDto.setPassword(encodedPassword);
            }

            if (tbUserInfoMapper.insertData(inputDto) == 0) {
                return new ApiResponse<Void>(false, "등록 오류");
            }

            log.debug(BizUtils.logInfo("END"));
            return new ApiResponse<Void>(true, "등록 성공");

        } catch (Exception e) {
            return new ApiResponse<Void>(false, "등록 처리 중 오류가 발생했습니다.");
        }
    }


    /**
     * method   : updateData
     * desc     : 사용자정보 변경
     */
    public ApiResponse<Void> updateData(TbUserInfoDto inputDto)  {
        log.debug(BizUtils.logInfo("START", BizUtils.logVoKey(inputDto)));

        try {
            TbUserInfoDto outputDto = tbUserInfoMapper.getData(inputDto);

            if (outputDto == null) {
                return new ApiResponse<Void>(false, "수정할 자료가 없습니다." );
            }

            if(!StringUtil.isNullOrEmpty(inputDto.getPassword())) {
                String rawPassword = inputDto.getPassword();
                String encodedPassword = encoder.encode(rawPassword);
                inputDto.setPassword(encodedPassword);
            }

            if (tbUserInfoMapper.updateData(inputDto) == 0) {
                return new ApiResponse<Void>(false, "수정 오류");
            }

            log.debug(BizUtils.logInfo("END"));
            return new ApiResponse<Void>(true, "수정 성공");

        } catch (Exception e) {
            log.debug(BizUtils.logInfo("Exception"));
            return new ApiResponse<Void>(false, "수정 처리 중 오류가 발생했습니다.");
        }
    }

    /**
     * method   : getLoginData
     * desc     : Login 검증
     */
    public  ApiResponse<TbUserInfoDto> getLogin(TbUserInfoDto inputDto) {
        log.debug(BizUtils.logInfo("START"));
        try {
            TbLoginLogDto tbLoginLogDto = new TbLoginLogDto();
            tbLoginLogDto.setUserId(inputDto.getUserId());
            tbLoginLogDto.setIpAddr(inputDto.getIpAddr());
            tbLoginLogDto.setUserAgent(inputDto.getUserAgent());
            tbLoginLogDto.setLoginResult("1");

            // 사용자 조회
            TbUserInfoDto outputDto = tbUserInfoMapper.getData(inputDto);
            if (outputDto == null) {
                tbLoginLogDto.setFailReason("사용자가 없습니다.");
                this.insertLoginLog(tbLoginLogDto);
                return new ApiResponse<>(false, tbLoginLogDto.getFailReason());
            }

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String rawPassword = inputDto.getPassword();
            String dbPw = outputDto.getPassword();
            // 비밀번호 검증
            if (!encoder.matches(rawPassword, dbPw)) {
                tbLoginLogDto.setFailReason("비밀번호 오류");
                this.insertLoginLog(tbLoginLogDto);
                return new ApiResponse<>(false, tbLoginLogDto.getFailReason());
            }

            // 상태코드 검증
            if (StringUtils.equals(outputDto.getStat(), "0")) {
                tbLoginLogDto.setFailReason("시스템사용 미승인");
                this.insertLoginLog(tbLoginLogDto);
                return new ApiResponse<>(false, tbLoginLogDto.getFailReason());
            }

            if (StringUtils.equals(outputDto.getStat(), "9")) {
                tbLoginLogDto.setFailReason("시스템사용 불가");
                this.insertLoginLog(tbLoginLogDto);
                return new ApiResponse<>(false, tbLoginLogDto.getFailReason());
            }

            tbLoginLogDto.setLoginResult("0");
            tbLoginLogDto.setFailReason("");

            this.insertLoginLog(tbLoginLogDto);

            return new ApiResponse<>(true, "로그인 성공", outputDto );

        } catch (Exception e) {
            log.error("Login 처리 중 오류", e);
            return new  ApiResponse<>(false, "로그인 처리 중 오류가 발생했습니다.");
        }
    }
    /**
     * method   : getLoginLogList
     * desc     : 로그인 로그 조회
     */
    public List<TbLoginLogDto> getLoginLogList(TbLoginLogDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        return tbLoginLogMapper.getLoginLogList(inputDto);
    }

    /**
     * method   : insertLoginLog
     * desc     : Login Log insert
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertLoginLog(TbLoginLogDto inputDto) {
        tbLoginLogMapper.insertData(inputDto);
    }
}

