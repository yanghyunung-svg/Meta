package com.meta.controller;

import com.meta.common.response.ApiResponse;
import com.meta.common.util.BizUtils;
import com.meta.dto.TbFnstCdInfoDto;
import com.meta.service.FnstService;
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
public class FnstController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private FnstService fnstService;

    @GetMapping("/fnst/fnstList")
    public String fnstListPage() { return "meta/fnst/fnstList"; }

    @GetMapping("/fnst/fnstMng")
    public String fnstMngPage() { return "meta/fnst/fnstMng"; }

    @GetMapping("/fnst/fnstRegEblc")
    public String fnstRegEblcPage() { return "meta/fnst/fnstRegEblc"; }

    /**
     * @ ID : getFnstListData
     * @ NAME : 금융기관코드 목록 조회
     */
    @PostMapping("/getFnstListData")
    @ResponseBody
    public List<TbFnstCdInfoDto> getListData(@RequestBody TbFnstCdInfoDto inputDto, HttpServletRequest request) throws Exception {
        log.debug(BizUtils.logInfo(BizUtils.logVoKey(inputDto)));
        return fnstService.getListData(inputDto);
    }

    /**
     * @ ID : getFnstData
     * @ NAME : 금융기관코드 상세 조회
     */
    @PostMapping("/getFnstData")
    @ResponseBody
    public TbFnstCdInfoDto getFnstData(@RequestBody TbFnstCdInfoDto inputDto, HttpServletRequest request) throws Exception {
        log.debug(BizUtils.logInfo());
        return fnstService.getData(inputDto);
    }

    /**
     * @ ID : manageFnstData
     * @ NAME : 금융기관코드 관리
     */
    @PostMapping("/manageFnstData")
    @ResponseBody
    public  ApiResponse<Void> manageFnstData(@RequestBody TbFnstCdInfoDto inputDto, HttpServletRequest request) throws Exception {
        log.debug(BizUtils.logInfo());
        fnstService.manageData(inputDto);
        return ApiResponse.success(null);
    }

    /**
     * @ ID : uploadFnstPreview
     * @ NAME : 금융기관코드 엑셀업로드
     */
    @PostMapping("/uploadFnstPreview")
    public ResponseEntity<Map<String, Object>> uploadFnstPreview(@RequestParam("file") MultipartFile file) {
        log.debug(BizUtils.logInfo());
        Map<String, Object> res = new HashMap<>();
        try {
            List<TbFnstCdInfoDto> list = fnstService.uploadPreview(file);
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
     * @ ID : saveUploaded
     * @ NAME : 금융기관코드 엑셀업로드 저장
     */
    @PostMapping("/saveFnstUploaded")
    public ResponseEntity<Map<String, Object>> saveUploaded(@RequestBody List<TbFnstCdInfoDto> list, HttpServletRequest request) {
        log.debug(BizUtils.logInfo());
        Map<String, Object> res = new HashMap<>();
        try {
            int count = fnstService.saveUploaded(list);
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
