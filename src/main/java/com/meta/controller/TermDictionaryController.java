package com.meta.controller;

import com.meta.common.response.ApiResponse;
import com.meta.dto.TbTermDictionaryDto;
import com.meta.mapper.TbTermDictionaryMapper;
import com.meta.service.TermDictionaryService;
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
public class TermDictionaryController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TermDictionaryService termDictionaryService;
    @Autowired
    private TbTermDictionaryMapper tbTermDictionaryMapper;

    /**
     * @ ID : getTermListData
     * @ NAME : 용어사전 목록 조회
     */
    @PostMapping("/getTermListData")
    @ResponseBody
    public List<TbTermDictionaryDto> getTermListData(@RequestBody TbTermDictionaryDto inputDto, HttpServletRequest request) throws Exception {
        return termDictionaryService.getListData(inputDto);
    }

    /**
     * @ ID : getTermData
     * @ NAME : 용어사전 상세 조회
     */
    @PostMapping("/getTermData")
    @ResponseBody
    public TbTermDictionaryDto getTermData(@RequestBody TbTermDictionaryDto inputDto, HttpServletRequest request) throws Exception {
        return termDictionaryService.getData(inputDto);
    }

    /**
     * @ ID : manageData
     * @ NAME : 용어사전 관리
     */
    @PostMapping("/manageTermData")
    @ResponseBody
    public  ApiResponse<Void> manageData(@RequestBody TbTermDictionaryDto inputDto, HttpServletRequest request) throws Exception {
        termDictionaryService.manageData(inputDto);
        return ApiResponse.success(null);
    }

    /**
     * @ ID : getTermSplitData
     * @ NAME : 용어사전 단어 목록 조회
     */
    @PostMapping("/getTermSplitData")
    @ResponseBody
    public TbTermDictionaryDto getTermSplitData(@RequestBody TbTermDictionaryDto inputDto, HttpServletRequest request) throws Exception {
        return termDictionaryService.getTermSplitData(inputDto);
    }

    @PostMapping("/uploadTermExcelOnly")
    public ResponseEntity<Map<String, Object>> uploadTermExcelOnly(@RequestParam("file") MultipartFile file) {
        Map<String, Object> res = new HashMap<>();
        try {
            List<TbTermDictionaryDto> list = termDictionaryService.uploadTermExcelOnly(file);
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
            List<TbTermDictionaryDto> list = termDictionaryService.parseExcelPreview(file);
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
    public ResponseEntity<Map<String, Object>> parseTermReload(@RequestBody List<TbTermDictionaryDto> inputList) {
        Map<String, Object> res = new HashMap<>();
        try {
            List<TbTermDictionaryDto> list = termDictionaryService.parseTermReload(inputList);
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
    public ResponseEntity<Map<String, Object>> uploadTermExcelSave(@RequestBody List<TbTermDictionaryDto> list, HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        try {
            int count = termDictionaryService.uploadTermExcelSave(list);
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
