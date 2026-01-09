package com.meta.controller;

import com.meta.common.response.ApiResponse;
import com.meta.dto.TbCodeDto;
import com.meta.service.CodeService;
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
public class CodeController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CodeService codeService;
 
    /**
     * @ ID : getCodeListData
     * @ NAME : 코드 목록 조회
     */
    @PostMapping("/getCodeListData")
    @ResponseBody
    public List<TbCodeDto> getCodeListData(@RequestBody TbCodeDto inputDto, HttpServletRequest request) throws Exception {
        return codeService.getListData(inputDto);
    }

    /**
     * @ ID : getCodeData
     * @ NAME : 코드 상세 조회
     */
    @PostMapping("/getCodeData")
    @ResponseBody
    public TbCodeDto getCodeData(@RequestBody TbCodeDto inputDto, HttpServletRequest request) throws Exception {
        return codeService.getData(inputDto);
    }

    /**
     * @ ID : manageCodeDetlData
     * @ NAME : 코드 관리
     */
    @PostMapping("/manageCodeDetlData")
    @ResponseBody
    public ApiResponse<Void> manageCodeDetlData(@RequestBody TbCodeDto inputDto, HttpServletRequest request) throws Exception {
        codeService.manageData(inputDto);
        return ApiResponse.success(null);
    }


    /**
     * @ ID : getCodeAllData
     * @ NAME : 코드 콤보 조회
     */
    @PostMapping("/getCodeAllData")
    public ResponseEntity<List<TbCodeDto>> getCodeAllData(@RequestBody TbCodeDto inputDto)  {
        List<TbCodeDto> outputDto = codeService.getCodeAllData(inputDto);
        return ResponseEntity.ok(outputDto);
    }

    /**
     * @ ID : uploadCodePreview
     * @ NAME : 상세코드 엑셀업로드
     */
    @PostMapping("/uploadCodePreview")
    public ResponseEntity<Map<String, Object>> uploadCodePreview(@RequestParam("file") MultipartFile file) {
        Map<String, Object> res = new HashMap<>();
        try {
            List<TbCodeDto> list = codeService.parsePreview(file);
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
     * @ NAME : 상세코드 엑셀업로드 저장
     */
    @PostMapping("/uploadCodeExcelSave")
    public ResponseEntity<Map<String, Object>> uploadCodeExcelSave(@RequestBody List<TbCodeDto> list) {
        Map<String, Object> res = new HashMap<>();
        try {
            int count = codeService.saveUploaded(list);
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
