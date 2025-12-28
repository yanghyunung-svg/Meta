package com.meta.controller;

import com.common.utils.ApiResponse;
import com.common.utils.BizUtils;
import com.meta.dto.TbLoginLogDto;
import com.meta.dto.TbUserInfoDto;
import com.meta.dto.TbUserInfoDto;
import com.meta.service.UserInfoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Controller
@RequestMapping("/meta")
public class UserInfoController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserInfoService userInfoService;

    /**
     * @ ID : getUserListData
     * @ NAME : 사용자정보 목록 조회
     */
    @PostMapping("/getUserListData")
    @ResponseBody
    public List<TbUserInfoDto> getUserListData(@RequestBody TbUserInfoDto inputDto, HttpServletRequest request) throws Exception {
        return userInfoService.getListData(inputDto);
    }

    /**
     * @ ID : getUserData
     * @ NAME : 사용자정보 상세 조회
     */
    @PostMapping("/getUserData")
    @ResponseBody
    public TbUserInfoDto getUserData(@RequestBody TbUserInfoDto inputDto, HttpServletRequest request) throws Exception {
        return userInfoService.getData(inputDto);
    }

    /**
     * @ ID : insertUserData
     * @ NAME : 사용자정보 등록
     */
    @PostMapping("/insertUserData")
    @ResponseBody
    public ApiResponse<Void> insertUserData(@RequestBody TbUserInfoDto inputDto, HttpServletRequest request) throws Exception {
        return userInfoService.insertData(inputDto);
    }
    /**
     * @ ID : changePassword
     * @ NAME : 비밀번호 변경
     */
    @PostMapping("/changePassword")
    @ResponseBody
    public ApiResponse<Void> changePassword(@RequestBody TbUserInfoDto inputDto, HttpServletRequest request) throws Exception {
        return userInfoService.changePassword(inputDto);
    }

    /**
     * @ ID : updateUserData
     * @ NAME : 사용자정보 변경
     */
    @PostMapping("/updateUserData")
    @ResponseBody
    public ApiResponse<Void> updateUserData(@RequestBody TbUserInfoDto inputDto, HttpServletRequest request) throws Exception {
        return userInfoService.updateData(inputDto);
    }


    /**
     * @ ID : uploadCodeExcelPreview
     * @ NAME : 사용자정보 엑셀업로드
     */
    @PostMapping("/uploadUserExcelPreview")
    public ResponseEntity<Map<String, Object>> uploadUserExcelPreview(@RequestParam("file") MultipartFile file) {
        Map<String, Object> res = new HashMap<>();
        try {
            List<TbUserInfoDto> list = userInfoService.parseExcelPreview(file);
            res.put("success", true);
            res.put("data", list);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            res.put("success", false);
            res.put("message", e.getMessage());
            return ResponseEntity.ok(res);
        }
    }

    /**
     * @ ID : uploadCodeExcelSave
     * @ NAME : 사용자정보 엑셀업로드 저장
     */
    @PostMapping("/uploadUserExcelSave")
    public ResponseEntity<Map<String, Object>> uploadUserExcelSave(@RequestBody List<TbUserInfoDto> list) {
        Map<String, Object> res = new HashMap<>();
        try {
            int count = userInfoService.saveUploadedExcel(list);
            res.put("success", true);
            res.put("count", count);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            res.put("success", false);
            res.put("message", e.getMessage());
            return ResponseEntity.ok(res);
        }
    }

}
