package com.meta.service;

import ch.qos.logback.core.util.StringUtil;
import com.meta.common.constants.BizConstants;
import com.meta.common.constants.ResponseCode;
import com.meta.common.exception.BizException;
import com.meta.common.util.BizUtils;
import com.meta.dto.TbUserInfoDto;
import com.meta.mapper.TbLoginLogMapper;
import com.meta.mapper.TbUserInfoMapper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/** 
 *@ ID       : userInfoService
 *@ NAME     : 사용자정보 Service
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
        return tbUserInfoMapper.getListData(inputDto);
    }

    /**
     * method   : getData
     * desc     : 사용자정보 상세 조회
     */
    public TbUserInfoDto getData(TbUserInfoDto inputDto)  {
        return tbUserInfoMapper.getData(inputDto);
    }
    /**
     * method   : manageData
     * desc     : 사용자정보 관리
     */
    public void manageData(TbUserInfoDto inputDto) {
        log.debug(BizUtils.logInfo("START"));
        log.debug(BizUtils.logVoKey(inputDto));
        TbUserInfoDto outputDto = tbUserInfoMapper.getLockData(inputDto);

        switch (inputDto.getFunc()) {
            case BizConstants.FUNC_SE.INS:
                if (outputDto != null) {
                    throw new BizException(ResponseCode.DUPLICATE_DATA);
                }

                if(!StringUtil.isNullOrEmpty(inputDto.getPassword())) {
                    String rawPassword = inputDto.getPassword();
                    String encodedPassword = encoder.encode(rawPassword);
                    inputDto.setPassword(encodedPassword);
                }

                if (tbUserInfoMapper.insertData(inputDto) == 0) {
                    throw new BizException(ResponseCode.INSERT_FAILED);
                }
                break;
            case BizConstants.FUNC_SE.UPD:
                if (outputDto == null) {
                    throw new BizException(ResponseCode.DATA_NOT_FOUND);
                }
                if(!StringUtil.isNullOrEmpty(inputDto.getPassword())) {
                    String rawPassword = inputDto.getPassword();
                    String encodedPassword = encoder.encode(rawPassword);
                    inputDto.setPassword(encodedPassword);
                }
                if (tbUserInfoMapper.updateData(inputDto) == 0) {
                    throw new BizException(ResponseCode.UPDATE_FAILED);
                }
                break;
            case BizConstants.FUNC_SE.DEL:
                if (outputDto == null) {
                    throw new BizException(ResponseCode.DATA_NOT_FOUND);
                }
                if (tbUserInfoMapper.deleteData(inputDto) == 0) {
                    throw new BizException(ResponseCode.DELETE_FAILED);
                }
                break;
            default:
                throw new BizException(ResponseCode.VALIDATION_FAILED);
        }
        log.debug(BizUtils.logInfo("END"));
    }
    /**
     * method   : changePassword
     * desc     : 비밀번호 변경
     */
    public void changePassword(TbUserInfoDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));

        TbUserInfoDto outputDto = tbUserInfoMapper.getLockData(inputDto);

        if (outputDto == null) {
            throw new BizException(ResponseCode.DATA_NOT_FOUND);
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = inputDto.getCurrentPassword();
        String dbPw = outputDto.getPassword();
        // 비밀번호 검증
        if (!encoder.matches(rawPassword, dbPw)) {
            throw new BizException(ResponseCode.PASSWORD_FAILED);
        }

        if(!StringUtil.isNullOrEmpty(inputDto.getNewPassword())) {
            String newPassword = inputDto.getNewPassword();
            String encodedPassword = encoder.encode(newPassword);
            inputDto.setPassword(encodedPassword);
        }

        if (tbUserInfoMapper.updateData(inputDto) == 0) {
            throw new BizException(ResponseCode.UPDATE_FAILED);
        }

        log.debug(BizUtils.logInfo("END"));
    }
  


    /**
     * @ ID : parseExcelPreview
     * @ NAME : 사용자정보 엑셀업로드
     */
    public List<TbUserInfoDto> parseExcelPreview(MultipartFile file) throws Exception {

        List<TbUserInfoDto> result = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            TbUserInfoDto dto = new TbUserInfoDto();

            dto.setUserId   (BizUtils.getCell(row, 1));
            dto.setUserNm   (BizUtils.getCell(row, 2));
            dto.setEmail    (BizUtils.getCell(row, 3));
            dto.setPhone    (BizUtils.getCell(row, 4));
            dto.setRole     (BizUtils.getCell(row, 5));
            dto.setSttsCd     ("0");

            // 검증 로직
            String error = validateRow(dto);
            if (StringUtil.notNullNorEmpty(error))  dto.setSttsCd(error);

            result.add(dto);
        }

        return result;
    }

    private String validateRow(TbUserInfoDto dto) {
        if (dto.getUserId() == null || dto.getUserId().isEmpty()) return "사용자ID 누락";
        if (dto.getUserNm() == null || dto.getUserNm().isEmpty()) return "사용자명 누락";
        if (dto.getEmail() == null || dto.getEmail().isEmpty()) return "이메일 누락";
        if (dto.getPhone() == null || dto.getPhone().isEmpty()) return "전화번호 누락";
        if (dto.getRole() == null || dto.getRole().isEmpty()) return "권한 누락";
        
        // DB 중복 체크
        int exists = tbUserInfoMapper.countCode(dto);
        if (exists > 0) return "이미 존재하는 코드";
        return null;  // 정상
    }

    public int saveUploadedExcel(List<TbUserInfoDto> list) {
        String rawPassword = "1";
        String encodedPassword = encoder.encode(rawPassword);

        int count = 0;
        for (TbUserInfoDto dto : list) {
            if(StringUtils.equals(dto.getSttsCd(), "0")) {
                dto.setUpdId(dto.getCrtId());
                dto.setSttsCd("1");
                dto.setPassword(encodedPassword);
                tbUserInfoMapper.insertData(dto);
                count++;
            }
        }
        return count;
    }
}

