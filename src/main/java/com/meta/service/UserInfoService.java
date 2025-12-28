package com.meta.service;

import ch.qos.logback.core.util.StringUtil;
import com.common.utils.ApiResponse;
import com.common.utils.BizUtils;
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
     * method   : insertData
     * desc     : 사용자정보 등록
     */
    public ApiResponse<Void> insertData(TbUserInfoDto inputDto)  {
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

            return new ApiResponse<Void>(true, "등록 성공");

        } catch (Exception e) {
            log.debug(BizUtils.logInfo("Exception"));
            return new ApiResponse<Void>(false, "등록 처리 중 오류가 발생했습니다.");
        }
    }


    /**
     * method   : updateData
     * desc     : 사용자정보 변경
     */
    public ApiResponse<Void> updateData(TbUserInfoDto inputDto)  {
        try {
            TbUserInfoDto outputDto = tbUserInfoMapper.getLockData(inputDto);

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
     * method   : changePassword
     * desc     : 비밀번호 변경
     */
    public ApiResponse<Void> changePassword(TbUserInfoDto inputDto)  {
        try {
            TbUserInfoDto outputDto = tbUserInfoMapper.getLockData(inputDto);

            if (outputDto == null) {
                return new ApiResponse<Void>(false, "수정할 자료가 없습니다." );
            }

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String rawPassword = inputDto.getCurrentPassword();
            String dbPw = outputDto.getPassword();
            // 비밀번호 검증
            if (!encoder.matches(rawPassword, dbPw)) {
                return new ApiResponse<Void>(false, "비밀번호 오류");
            }

            if(!StringUtil.isNullOrEmpty(inputDto.getNewPassword())) {
                String newPassword = inputDto.getNewPassword();
                String encodedPassword = encoder.encode(newPassword);
                inputDto.setPassword(encodedPassword);
            }

            if (tbUserInfoMapper.updateData(inputDto) == 0) {
                return new ApiResponse<Void>(false, "비밀번호 수정 오류");
            }

            log.debug(BizUtils.logInfo("END"));
            return new ApiResponse<Void>(true, "비밀번호 수정 성공");

        } catch (Exception e) {
            log.debug(BizUtils.logInfo("Exception"));
            return new ApiResponse<Void>(false, "수정 처리 중 오류가 발생했습니다.");
        }
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
            dto.setStat     ("0");

            // 검증 로직
            String error = validateRow(dto);
            if (StringUtil.notNullNorEmpty(error))  dto.setStat(error);

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
            if(StringUtils.equals(dto.getStat(), "0")) {
                dto.setUpdId(dto.getCrtId());
                dto.setStat("1");
                dto.setPassword(encodedPassword);
                tbUserInfoMapper.insertData(dto);
                count++;
            }
        }
        return count;
    }
}

