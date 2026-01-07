package com.meta.controller;

import com.meta.common.response.ApiResponse;
import com.meta.dto.TbStdTermBscDto;
import com.meta.mapper.TbStdTermBscMapper;
import com.meta.service.StdTermBscService;
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
public class StdTermBscController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private StdTermBscService stdTermBscService;
    @Autowired
    private TbStdTermBscMapper termMapper;

    /**
     * @ ID : getTermListData
     * @ NAME : 용어사전 목록 조회
     */
    @PostMapping("/getTermListData")
    @ResponseBody
    public List<TbStdTermBscDto> getTermListData(@RequestBody TbStdTermBscDto inputDto, HttpServletRequest request) throws Exception {
        return stdTermBscService.getListData(inputDto);
    }

    /**
     * @ ID : getTermData
     * @ NAME : 용어사전 상세 조회
     */
    @PostMapping("/getTermData")
    @ResponseBody
    public TbStdTermBscDto getTermData(@RequestBody TbStdTermBscDto inputDto, HttpServletRequest request) throws Exception {
        return stdTermBscService.getData(inputDto);
    }

    /**
     * @ ID : manageData
     * @ NAME : 용어사전 관리
     */
    @PostMapping("/manageTermData")
    @ResponseBody
    public  ApiResponse<Void> manageData(@RequestBody TbStdTermBscDto inputDto, HttpServletRequest request) throws Exception {
        stdTermBscService.manageData(inputDto);
        return ApiResponse.success(null);
    }

    /**
     * @ ID : getTermSplitData
     * @ NAME : 용어사전 단어 목록 조회
     */
    @PostMapping("/getTermSplitData")
    @ResponseBody
    public TbStdTermBscDto getTermSplitData(@RequestBody TbStdTermBscDto inputDto, HttpServletRequest request) throws Exception {
        return stdTermBscService.getTermSplitData(inputDto);
    }

    @PostMapping("/uploadTermExcelOnly")
    public ResponseEntity<Map<String, Object>> uploadTermExcelOnly(@RequestParam("file") MultipartFile file) {
        Map<String, Object> res = new HashMap<>();
        try {
            List<TbStdTermBscDto> list = stdTermBscService.uploadTermExcelOnly(file);
            res.put("success", true);
            res.put("data", list);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            res.put("success", false);
            res.put("message", e.getMessage());
            return ResponseEntity.ok(res);
        }
    }
    @PostMapping("/uploadTermExcelPreview")
    public ResponseEntity<Map<String, Object>> uploadTermExcelPreview(@RequestParam("file") MultipartFile file) {
        Map<String, Object> res = new HashMap<>();
        try {
            List<TbStdTermBscDto> list = stdTermBscService.parseExcelPreview(file);
            res.put("success", true);
            res.put("data", list);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            res.put("success", false);
            res.put("message", e.getMessage());
            return ResponseEntity.ok(res);
        }
    }

    @PostMapping("/parseTermReload")
    public ResponseEntity<Map<String, Object>> parseTermReload(@RequestBody List<TbStdTermBscDto> inputList) {
        Map<String, Object> res = new HashMap<>();
        try {
            List<TbStdTermBscDto> list = stdTermBscService.parseTermReload(inputList);
            res.put("success", true);
            res.put("data", list);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            res.put("success", false);
            res.put("message", e.getMessage());
            return ResponseEntity.ok(res);
        }
    }


    @PostMapping("/uploadTermExcelSave")
    public ResponseEntity<Map<String, Object>> uploadTermExcelSave(@RequestBody List<TbStdTermBscDto> list, HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        try {
            int count = stdTermBscService.uploadTermExcelSave(list);
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
