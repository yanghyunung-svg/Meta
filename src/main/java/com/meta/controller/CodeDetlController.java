package com.meta.controller;

import com.meta.common.response.ApiResponse;
import com.meta.dto.TbCodeDetlDto;
import com.meta.service.CodeDetlService;
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
public class CodeDetlController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CodeDetlService codeDetlService;
 
    /**
     * @ ID : getCodeDetlListData
     * @ NAME : 코드 목록 조회
     */
    @PostMapping("/getCodeDetlListData")
    @ResponseBody
    public List<TbCodeDetlDto> getCodeDetlListData(@RequestBody TbCodeDetlDto inputDto, HttpServletRequest request) throws Exception {
        return codeDetlService.getListData(inputDto);
    }

    /**
     * @ ID : getCodeDetlData
     * @ NAME : 코드 상세 조회
     */
    @PostMapping("/getCodeDetlData")
    @ResponseBody
    public TbCodeDetlDto getCodeDetlData(@RequestBody TbCodeDetlDto inputDto, HttpServletRequest request) throws Exception {
        return codeDetlService.getData(inputDto);
    }

    /**
     * @ ID : manageCodeDetlData
     * @ NAME : 코드 관리
     */
    @PostMapping("/manageCodeDetlData")
    @ResponseBody
    public ApiResponse<Void> manageCodeDetlData(@RequestBody TbCodeDetlDto inputDto, HttpServletRequest request) throws Exception {
        codeDetlService.manageData(inputDto);
        return ApiResponse.success(null);
    }

    /**
     * @ ID : uploadCodeDetlPreview
     * @ NAME : 상세코드 엑셀업로드
     */
    @PostMapping("/uploadCodeDetlPreview")
    public ResponseEntity<Map<String, Object>> uploadCodeDetlPreview(@RequestParam("file") MultipartFile file) {
        Map<String, Object> res = new HashMap<>();
        try {
            List<TbCodeDetlDto> list = codeDetlService.parsePreview(file);
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
     * @ NAME : 상세코드 엑셀업로드 저장
     */
    @PostMapping("/uploadCodeDetlExcelSave")
    public ResponseEntity<Map<String, Object>> uploadCodeDetlExcelSave(@RequestBody List<TbCodeDetlDto> list) {
        Map<String, Object> res = new HashMap<>();
        try {
            int count = codeDetlService.saveUploaded(list);
            res.put("success", true);
            res.put("count", count);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            res.put("success", false);
            res.put("message", e.getMessage());
            return ResponseEntity.ok(res);
        }
    }


    /**
     * @ ID : getCodeDetlAllData
     * @ NAME : 코드 콤보 조회
     */
    @PostMapping("/getCodeDetlAllData")
    public ResponseEntity<List<TbCodeDetlDto>> getCodeDetlAllData(@RequestBody TbCodeDetlDto inputDto)  {
        List<TbCodeDetlDto> outputDto = codeDetlService.getCodeDetlAllData(inputDto);
        return ResponseEntity.ok(outputDto);
    }
}
