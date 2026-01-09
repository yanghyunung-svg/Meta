package com.meta.controller;

import ch.qos.logback.core.util.StringUtil;
import com.meta.common.response.ApiResponse;
import com.meta.dto.TbStdWordBscDto;
import com.meta.service.WordService;
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
public class WordController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private WordService stdWordBscService;

    /**
     * @ ID : getWordListData
     * @ NAME : 단어사전 목록 조회
     */
    @PostMapping("/getWordListData")
    @ResponseBody
    public List<TbStdWordBscDto> getWordListData(@RequestBody TbStdWordBscDto inputDto, HttpServletRequest request) throws Exception {
        return stdWordBscService.getListData(inputDto);
    }

    /**
     * @ ID : getWordData
     * @ NAME : 단어사전 상세 조회
     */
    @PostMapping("/getWordData")
    @ResponseBody
    public TbStdWordBscDto getWordData(@RequestBody TbStdWordBscDto inputDto, HttpServletRequest request) throws Exception {
        if (StringUtil.notNullNorEmpty(inputDto.getWordNm())) {
            return stdWordBscService.getDataByName(inputDto);
        }
        else  {
            return stdWordBscService.getData(inputDto);
        }
    }

    /**
     * @ ID : manageWordData
     * @ NAME : 단어사전 관리
     */
    @PostMapping("/manageWordData")
    @ResponseBody
    public ApiResponse<Void> manageWordData(@RequestBody TbStdWordBscDto inputDto, HttpServletRequest request) throws Exception {
        stdWordBscService.manageData(inputDto);
        return ApiResponse.success(null);
    }

    /**
     * @ ID : uploadWordPreview
     * @ NAME : 표준단어 엑셀업로드
     */
    @PostMapping("/uploadWordPreview")
    public ResponseEntity<Map<String, Object>> uploadwordPreview(@RequestParam("file") MultipartFile file) {
        Map<String, Object> res = new HashMap<>();
        try {
            List<TbStdWordBscDto> list = stdWordBscService.parsePreview(file);
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
    public ResponseEntity<Map<String, Object>> uploadwordExcelSave(@RequestBody List<TbStdWordBscDto> list) {
        Map<String, Object> res = new HashMap<>();
        try {
            int count = stdWordBscService.saveUploaded(list);
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