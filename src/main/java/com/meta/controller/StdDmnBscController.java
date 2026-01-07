package com.meta.controller;

import com.meta.common.response.ApiResponse;
import com.meta.dto.TbStdDmnBscDto;
import com.meta.service.StdDmnBscService;
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
public class StdDmnBscController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private StdDmnBscService stdDmnBscService;

    /**
     * @ ID : getDmnListData
     * @ NAME : 표준도메인 목록 조회
     */
    @PostMapping("/getDmnListData")
    @ResponseBody
    public List<TbStdDmnBscDto> getDmnListData(@RequestBody TbStdDmnBscDto inputDto, HttpServletRequest request) throws Exception {
        return stdDmnBscService.getListData(inputDto);
    }
    /**
     * @ ID : getDmnComboData
     * @ NAME : 표준도메인 콤보 조회
     */
    @PostMapping("/getDmnComboData")
    public ResponseEntity<List<TbStdDmnBscDto>> getDmnComboData()  {
        List<TbStdDmnBscDto> outputDto = stdDmnBscService.getDmnComboData();
        return ResponseEntity.ok(outputDto);
    }

    /**
     * @ ID : getDmnData
     * @ NAME : 표준도메인 상세 조회
     */
    @PostMapping("/getDmnData")
    @ResponseBody
    public TbStdDmnBscDto getDmnData(@RequestBody TbStdDmnBscDto inputDto, HttpServletRequest request) throws Exception {
        return stdDmnBscService.getData(inputDto);
    }

    /**
     * @ ID : manageDmnData
     * @ NAME : 표준도메인 관리
     */
    @PostMapping("/manageDmnData")
    @ResponseBody
    public ApiResponse<Void> manageDmnData(@RequestBody TbStdDmnBscDto inputDto, HttpServletRequest request) throws Exception {
        stdDmnBscService.manageData(inputDto);
        return ApiResponse.success(null);
    }

    /**
     * @ ID : uploadCodeExcelPreview
     * @ NAME : 표준도메인 엑셀업로드
     */
    @PostMapping("/uploadDmnExcelPreview")
    public ResponseEntity<Map<String, Object>> uploadDmnExcelPreview(@RequestParam("file") MultipartFile file) {
        Map<String, Object> res = new HashMap<>();
        try {
            List<TbStdDmnBscDto> list = stdDmnBscService.parseExcelPreview(file);
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
     * @ NAME : 표준도메인 엑셀업로드 저장
     */
    @PostMapping("/uploadDmnExcelSave")
    public ResponseEntity<Map<String, Object>> uploadDmnExcelSave(@RequestBody List<TbStdDmnBscDto> list) {
        Map<String, Object> res = new HashMap<>();
        try {
            int count = stdDmnBscService.saveUploadedExcel(list);
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
