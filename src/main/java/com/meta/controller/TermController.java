package com.meta.controller;

import com.meta.common.response.ApiResponse;
import com.meta.common.util.BizUtils;
import com.meta.dto.TbStdTermBscDto;
import com.meta.mapper.dbio.TbStdTermBscMapper;
import com.meta.service.TermService;
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
public class TermController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TermService termService;
    @Autowired
    private TbStdTermBscMapper termMapper;

    /* Web Page */
    @GetMapping("/term/termList")
    public String termList() { return "meta/term/termList"; }
    @GetMapping("/term/termMng")
    public String termMng() { return "meta/term/termMng"; }
    @GetMapping("/term/termRegEblc")
    public String termRegEblc() { return "meta/term/termRegEblc"; }
    @GetMapping("/term/telgmTermReg")
    public String telgmTermReg() { return "meta/term/telgmTermReg"; }
    @GetMapping("/term/termSearch")
    public String termSearch() { return "meta/term/termSearch"; }

    /**
     * @ ID : getTermListData
     * @ NAME : 용어사전 목록 조회
     */
    @PostMapping("/getTermListData")
    @ResponseBody
    public List<TbStdTermBscDto> getTermListData(@RequestBody TbStdTermBscDto inputDto, HttpServletRequest request) throws Exception {
        log.debug(BizUtils.logInfo(BizUtils.logVoKey(inputDto)));
        return termService.getListData(inputDto);
    }

    /**
     * @ ID : getTermData
     * @ NAME : 용어사전 상세 조회
     */
    @PostMapping("/getTermData")
    @ResponseBody
    public TbStdTermBscDto getTermData(@RequestBody TbStdTermBscDto inputDto, HttpServletRequest request) throws Exception {
        log.debug(BizUtils.logInfo());
        return termService.getData(inputDto);
    }

    /**
     * @ ID : manageData
     * @ NAME : 용어사전 관리
     */
    @PostMapping("/manageTermData")
    @ResponseBody
    public  ApiResponse<Void> manageData(@RequestBody TbStdTermBscDto inputDto, HttpServletRequest request) throws Exception {
        log.debug(BizUtils.logInfo());
        termService.manageData(inputDto);
        return ApiResponse.success(null);
    }

    /**
     * @ ID : getTermSplitData
     * @ NAME : 용어사전 단어 목록 조회
     */
    @PostMapping("/getTermSplitData")
    @ResponseBody
    public TbStdTermBscDto getTermSplitData(@RequestBody TbStdTermBscDto inputDto, HttpServletRequest request) throws Exception {
        log.debug(BizUtils.logInfo());
        return termService.getTermSplitData(inputDto);
    }

    @PostMapping("/uploadTermExcelOnly")
    public ResponseEntity<Map<String, Object>> uploadTermExcelOnly(@RequestParam("file") MultipartFile file) {
        log.debug(BizUtils.logInfo("START"));
        Map<String, Object> res = new HashMap<>();
        try {
            List<TbStdTermBscDto> list = termService.uploadTermExcelOnly(file);
            res.put("success", true);
            res.put("data", list);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            res.put("success", false);
            res.put("message", e.getMessage());
        } finally {
            log.debug(BizUtils.logInfo("END"));
            return ResponseEntity.ok(res);
        }
    }
    @PostMapping("/uploadTermPreview")
    public ResponseEntity<Map<String, Object>> uploadTermPreview(@RequestParam("file") MultipartFile file) {
        log.debug(BizUtils.logInfo("START"));
        Map<String, Object> res = new HashMap<>();
        try {
            List<TbStdTermBscDto> list = termService.parsePreview(file);
            res.put("success", true);
            res.put("data", list);
        } catch (Exception e) {
            res.put("success", false);
            res.put("message", e.getMessage());
        } finally {
            log.debug(BizUtils.logInfo("END"));
            return ResponseEntity.ok(res);
        }
    }

    @PostMapping("/parseTermReload")
    public ResponseEntity<Map<String, Object>> parseTermReload(@RequestBody List<TbStdTermBscDto> inputList) {
        log.debug(BizUtils.logInfo("START"));
        Map<String, Object> res = new HashMap<>();
        try {
            List<TbStdTermBscDto> list = termService.parseTermReload(inputList);
            res.put("success", true);
            res.put("data", list);
        } catch (Exception e) {
            res.put("success", false);
            res.put("message", e.getMessage());
        } finally {
            log.debug(BizUtils.logInfo("END"));
            return ResponseEntity.ok(res);
        }
    }


    @PostMapping("/eblcRegTerm")
    public ResponseEntity<Map<String, Object>> eblcRegTerm(@RequestBody List<TbStdTermBscDto> list, HttpServletRequest request) {
        log.debug(BizUtils.logInfo("START"));
        Map<String, Object> res = new HashMap<>();
        int count = 0;
        try {
            count = termService.eblcRegTerm(list);
            res.put("success", true);
            res.put("count", count);
        } catch (Exception e) {
            res.put("success", false);
            res.put("message", e.getMessage());
            res.put("count", count);
        } finally {
            log.debug(BizUtils.logInfo("END", String.valueOf(count)));
            return ResponseEntity.ok(res);
        }
    }

}
