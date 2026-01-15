package com.meta.controller;

import com.meta.common.response.ApiResponse;
import com.meta.common.util.BizUtils;
import com.meta.dto.TbTelgmDtlBscDto;
import com.meta.service.TelgmDtlService;
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
public class TelgmDtlController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TelgmDtlService telgmDtlService;

    @GetMapping("/telgm/telgmDtlList")
    public String telgmDtlList()    { return "meta/telgm/TelgmDtlList"; }
    @GetMapping("/telgm/telgmDtlMng")
    public String telgmDtlMng()     { return "meta/telgm/TelgmDtlMng"; }
    @GetMapping("/telgm/telgmDtlRegEblc")
    public String telgmDtlRegEblc() { return "meta/telgm/TelgmDtlRegEblc"; }

    /**
     * @ ID : getTelgmDtlListData
     * @ NAME : 전문상세 목록 조회
     */
    @PostMapping("/getTelgmDtlListData")
    @ResponseBody
    public List<TbTelgmDtlBscDto> getTelgmDtlListData(@RequestBody TbTelgmDtlBscDto inputDto, HttpServletRequest request) throws Exception {
        log.debug(BizUtils.logInfo(BizUtils.logVoKey(inputDto)));
        return telgmDtlService.getListData(inputDto);
    }

    /**
     * @ ID : getTelgmDtlData
     * @ NAME : 전문상세 상세 조회
     */
    @PostMapping("/getTelgmDtlData")
    @ResponseBody
    public TbTelgmDtlBscDto getTelgmDtlData(@RequestBody TbTelgmDtlBscDto inputDto, HttpServletRequest request) throws Exception {
        log.debug(BizUtils.logInfo("START", BizUtils.logVoKey(inputDto)));
        TbTelgmDtlBscDto outputDto = telgmDtlService.getData(inputDto);
        log.debug(BizUtils.logInfo("END", BizUtils.logVoKey(outputDto)));
        return outputDto;
    }

    /**
     * @ ID : manageTelgmDtlData
     * @ NAME : 전문상세 관리
     */
    @PostMapping("/manageTelgmDtlData")
    @ResponseBody
    public  ApiResponse<Void> manageTelgmDtlData(@RequestBody TbTelgmDtlBscDto inputDto, HttpServletRequest request) throws Exception {
        log.debug(BizUtils.logInfo());
        telgmDtlService.manageData(inputDto);
        return ApiResponse.success(null);
    }

    /**
     * @ ID : uploadTelgmDtlPreview
     * @ NAME : 전문상세 엑셀업로드
     */
    @PostMapping("/uploadTelgmDtlPreview")
    public ResponseEntity<Map<String, Object>> uploadTelgmDtlPreview(@RequestParam("file") MultipartFile file) {
        log.debug(BizUtils.logInfo());
        Map<String, Object> res = new HashMap<>();
        try {
            List<TbTelgmDtlBscDto> list = telgmDtlService.uploadPreview(file);
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
     * @ ID : saveTelgmDtlUploaded
     * @ NAME : 전문상세 엑셀업로드 저장
     */
    @PostMapping("/saveTelgmDtlUploaded")
    public ResponseEntity<Map<String, Object>> saveTelgmDtlUploaded(@RequestBody List<TbTelgmDtlBscDto> list, HttpServletRequest request) {
        log.debug(BizUtils.logInfo());
        Map<String, Object> res = new HashMap<>();
        try {
            int count = telgmDtlService.saveUploaded(list);
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
