package com.meta.controller;

import ch.qos.logback.core.util.StringUtil;
import com.common.utils.ApiResponse;
import com.meta.dto.TbWordDictionaryDto;
import com.meta.service.WordDictionaryService;
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
public class WordDictionaryController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private WordDictionaryService wordDictionaryService;

    /**
     * @ ID : getWordListData
     * @ NAME : 단어사전 목록 조회
     */
    @PostMapping("/getWordListData")
    @ResponseBody
    public List<TbWordDictionaryDto> getWordListData(@RequestBody TbWordDictionaryDto inputDto, HttpServletRequest request) throws Exception {
        return wordDictionaryService.getListData(inputDto);
    }

    /**
     * @ ID : getWordData
     * @ NAME : 단어사전 상세 조회
     */
    @PostMapping("/getWordData")
    @ResponseBody
    public TbWordDictionaryDto getWordData(@RequestBody TbWordDictionaryDto inputDto, HttpServletRequest request) throws Exception {
        if (StringUtil.notNullNorEmpty(inputDto.getWordNm())) {
            return wordDictionaryService.getDataByName(inputDto);
        }
        else  {
            return wordDictionaryService.getData(inputDto);
        }
    }

    /**
     * @ ID : manageWordData
     * @ NAME : 단어사전 관리
     */
    @PostMapping("/manageWordData")
    @ResponseBody
    public ApiResponse<Void> manageWordData(@RequestBody TbWordDictionaryDto inputDto, HttpServletRequest request) throws Exception {
        return wordDictionaryService.manageData(inputDto);
    }

    /**
     * @ ID : uploadWordExcelPreview
     * @ NAME : 표준단어 엑셀업로드
     */
    @PostMapping("/uploadWordExcelPreview")
    public ResponseEntity<Map<String, Object>> uploadwordExcelPreview(@RequestParam("file") MultipartFile file) {
        Map<String, Object> res = new HashMap<>();
        try {
            List<TbWordDictionaryDto> list = wordDictionaryService.parseExcelPreview(file);
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
     * @ ID : uploadWordExcelSave
     * @ NAME : 표준단어 엑셀업로드 저장
     */
    @PostMapping("/uploadWordExcelSave")
    public ResponseEntity<Map<String, Object>> uploadwordExcelSave(@RequestBody List<TbWordDictionaryDto> list) {
        Map<String, Object> res = new HashMap<>();
        try {
            int count = wordDictionaryService.saveUploadedExcel(list);
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