package com.meta.controller;

import com.meta.common.response.ApiResponse;
import com.meta.dto.TbCodeGroupDto;
import com.meta.service.CodeGroupService;
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
public class CodeGroupController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CodeGroupService codeGroupService;
 
    /**
     * @ ID : getCodeGroupListData
     * @ NAME : 코드그룹 목록 조회
     */
    @PostMapping("/getCodeGroupListData")
    @ResponseBody
    public List<TbCodeGroupDto> getCodeGroupListData(@RequestBody TbCodeGroupDto inputDto, HttpServletRequest request) throws Exception {
        return codeGroupService.getListData(inputDto);
    }

    /**
     * @ ID : getCodeGroupData
     * @ NAME : 코드그룹 상세 조회
     */
    @PostMapping("/getCodeGroupData")
    @ResponseBody
    public TbCodeGroupDto getCodeGroupData(@RequestBody TbCodeGroupDto inputDto, HttpServletRequest request) throws Exception {
        return codeGroupService.getData(inputDto);
    }

    /**
     * @ ID : managerCodeGroupData
     * @ NAME : 코드그룹 관리
     */
    @PostMapping("/manageCodeGroupData")
    @ResponseBody
    public ApiResponse<Void> manageCodeGroupData(@RequestBody TbCodeGroupDto inputDto, HttpServletRequest request) throws Exception {
        codeGroupService.manageData(inputDto);
        return ApiResponse.success(null);
    }
    /**
     * @ ID : uploadCodeDetlPreview
     * @ NAME : 코드그룹 엑셀업로드
     */
    @PostMapping("/uploadCodeGroupPreview")
    public ResponseEntity<Map<String, Object>> uploadCodeGroupPreview(@RequestParam("file") MultipartFile file) {
        Map<String, Object> res = new HashMap<>();
        try {
            List<TbCodeGroupDto> list = codeGroupService.parsePreview(file);
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
     * @ ID : uploadCodeDetlExcelSave
     * @ NAME : 코드그룹 엑셀업로드 저장
     */
    @PostMapping("/uploadCodeGroupExcelSave")
    public ResponseEntity<Map<String, Object>> uploadCodeGroupExcelSave(@RequestBody List<TbCodeGroupDto> list) {
        Map<String, Object> res = new HashMap<>();
        try {
            int count = codeGroupService.saveUploaded(list);
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
