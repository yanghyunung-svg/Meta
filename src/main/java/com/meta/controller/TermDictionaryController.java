package com.meta.controller;

import com.common.utils.ApiResponse;
import com.common.utils.BizUtils;
import com.meta.dto.TbTermDictionaryDto;
import com.meta.mapper.TbTermDictionaryMapper;
import com.meta.service.TermDictionaryService;
import jakarta.servlet.http.HttpSession;
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
     * @ID : getTermListData
     * @NAME : 용어사전 목록 조회
     */
    @PostMapping("/getTermListData")
    @ResponseBody
    public List<TbTermDictionaryDto> getTermListData(@RequestBody TbTermDictionaryDto inputDto, HttpSession session) throws Exception {
        log.debug(BizUtils.logInfo("START"));
        List<TbTermDictionaryDto> outputDto = tbTermDictionaryMapper.getListData(inputDto);
        log.debug(BizUtils.logInfo("END"));
        return outputDto;
    }

    /**
     * @ID : getTermData
     * @NAME : 용어사전 상세 조회
     */
    @PostMapping("/getTermData")
    @ResponseBody
    public TbTermDictionaryDto getTermData(@RequestBody TbTermDictionaryDto inputDto, HttpSession session) throws Exception {
        log.debug(BizUtils.logInfo("START"));
        TbTermDictionaryDto outputDto = tbTermDictionaryMapper.getData(inputDto);
        log.debug(BizUtils.logInfo("END"));
        return outputDto;
    }

    /**
     * @ID : insertTermData
     * @NAME : 용어사전 등록
     */
    @PostMapping("/insertTermData")
    @ResponseBody
    public ApiResponse<Void> insertTermData(@RequestBody TbTermDictionaryDto inputDto, HttpSession session) throws Exception {
        log.debug(BizUtils.logInfo("START"));
        ApiResponse<Void> outputDto = termDictionaryService.insertData(inputDto);
        log.debug(BizUtils.logInfo("END"));
        return outputDto;
    }

    /**
     * @ID : updateTermData
     * @NAME : 용어사전 변경
     */
    @PostMapping("/updateTermData")
    @ResponseBody
    public ApiResponse<Void> updateTermData(@RequestBody TbTermDictionaryDto inputDto, HttpSession session) throws Exception {
        log.debug(BizUtils.logInfo("START"));
        ApiResponse<Void> outputDto = termDictionaryService.updateData(inputDto);
        log.debug(BizUtils.logInfo("END"));
        return outputDto;
    }

    /**
     * @ID : getTermSplitData
     * @NAME : 용어사전 단어 목록 조회
     */
    @PostMapping("/getTermSplitData")
    @ResponseBody
    public TbTermDictionaryDto getTermSplitData(@RequestBody TbTermDictionaryDto inputDto, HttpSession session) throws Exception {
        log.debug(BizUtils.logInfo("START"));
        TbTermDictionaryDto outputDto = termDictionaryService.getTermSplitData(inputDto);
        log.debug(BizUtils.logInfo("END"));
        return outputDto;
    }

    @PostMapping("/uploadTermExcelPreview")
    public ResponseEntity<Map<String, Object>> uploadTermExcelPreview(@RequestParam("file") MultipartFile file) {
        log.debug(BizUtils.logInfo("START"));
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
        } finally {
            log.debug(BizUtils.logInfo("END"));
        }
    }

    @PostMapping("/parseTermReload")
    public ResponseEntity<Map<String, Object>> parseTermReload(@RequestBody List<TbTermDictionaryDto> inputList) {
        log.debug(BizUtils.logInfo("START"));
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
        } finally {
            log.debug(BizUtils.logInfo("END"));
        }
    }

    @PostMapping("/uploadTermExcelSave")
    public ResponseEntity<Map<String, Object>> uploadTermExcelSave(@RequestBody List<TbTermDictionaryDto> list, HttpSession session) {
        log.debug(BizUtils.logInfo("START"));

        Map<String, Object> res = new HashMap<>();
        try {
            int count = termDictionaryService.uploadTermExcelSave(list, session);
            res.put("success", true);
            res.put("count", count);
            log.debug(BizUtils.logInfo("END", "success"));
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            res.put("success", false);
            res.put("message", e.getMessage());
            log.debug(BizUtils.logInfo("END", "fail"));
            return ResponseEntity.ok(res);
        }
    }

}
