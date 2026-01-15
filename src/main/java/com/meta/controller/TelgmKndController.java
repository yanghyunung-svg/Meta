package com.meta.controller;

import com.meta.common.response.ApiResponse;
import com.meta.common.util.BizUtils;
import com.meta.dto.TbTelgmKndBscDto;
import com.meta.service.TelgmKndService;
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
public class TelgmKndController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TelgmKndService telgmKndService;

    @GetMapping("/telgm/telgmKndList")
    public String telgmKndListPage() { return "meta/telgm/telgmKndList"; }
    @GetMapping("/telgm/telgmKndMng")
    public String telgmKndMngPage() { return "meta/telgm/telgmKndMng"; }
    @GetMapping("/telgm/telgmKndRegEblc")
    public String telgmRegKndEblcPage() { return "meta/telgm/telgmKndRegEblc"; }

    /**
     * @ ID : getTelgmKndListData
     * @ NAME : 전문종류 목록 조회
     */
    @PostMapping("/getTelgmKndListData")
    @ResponseBody
    public List<TbTelgmKndBscDto> getTelgmKndListData(@RequestBody TbTelgmKndBscDto inputDto, HttpServletRequest request) throws Exception {
        log.debug(BizUtils.logInfo(BizUtils.logVoKey(inputDto)));
        return telgmKndService.getListData(inputDto);
    }

    /**
     * @ ID : getTelgmKndData
     * @ NAME : 전문종류 상세 조회
     */
    @PostMapping("/getTelgmKndData")
    @ResponseBody
    public TbTelgmKndBscDto getTelgmKndData(@RequestBody TbTelgmKndBscDto inputDto, HttpServletRequest request) throws Exception {
        log.debug(BizUtils.logInfo("START", BizUtils.logVoKey(inputDto)));
        TbTelgmKndBscDto outputDto = telgmKndService.getData(inputDto);
        log.debug(BizUtils.logInfo("END", BizUtils.logVoKey(outputDto)));
        return outputDto;
    }

    /**
     * @ ID : manageTelgmKndData
     * @ NAME : 전문종류 관리
     */
    @PostMapping("/manageTelgmKndData")
    @ResponseBody
    public  ApiResponse<Void> manageTelgmKndData(@RequestBody TbTelgmKndBscDto inputDto, HttpServletRequest request) throws Exception {
        log.debug(BizUtils.logInfo());
        telgmKndService.manageData(inputDto);
        return ApiResponse.success(null);
    }

    /**
     * @ ID : uploadTelgmKndPreview
     * @ NAME : 전문종류 엑셀업로드
     */
    @PostMapping("/uploadTelgmKndPreview")
    public ResponseEntity<Map<String, Object>> uploadTelgmKndPreview(@RequestParam("file") MultipartFile file) {
        log.debug(BizUtils.logInfo());
        Map<String, Object> res = new HashMap<>();
        try {
            List<TbTelgmKndBscDto> list = telgmKndService.uploadPreview(file);
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
     * @ ID : saveTelgmKndUploaded
     * @ NAME : 전문종류 엑셀업로드 저장
     */
    @PostMapping("/saveTelgmKndUploaded")
    public ResponseEntity<Map<String, Object>> saveTelgmKndUploaded(@RequestBody List<TbTelgmKndBscDto> list, HttpServletRequest request) {
        log.debug(BizUtils.logInfo());
        Map<String, Object> res = new HashMap<>();
        try {
            int count = telgmKndService.saveUploaded(list);
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
