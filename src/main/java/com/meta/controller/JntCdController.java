package com.meta.controller;

import com.meta.common.response.ApiResponse;
import com.meta.common.util.BizUtils;
import com.meta.dto.TbFnncJntCdInfoDto;
import com.meta.service.JntCdService;
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
public class JntCdController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private JntCdService jntCdService;

    @GetMapping("/fnst/jntCdList")
    public String jntCdListPage() { return "meta/fnst/jntCdList"; }

    @GetMapping("/fnst/jntCdMng")
    public String jntCdMngPage() { return "meta/fnst/jntCdMng"; }

    @GetMapping("/fnst/jntCdRegEblc")
    public String jntCdRegEblcPage() { return "meta/fnst/jntCdRegEblc"; }

    /**
     * @ ID : getJntCdListData
     * @ NAME : 금융공동코드 목록 조회
     */
    @PostMapping("/getJntCdListData")
    @ResponseBody
    public List<TbFnncJntCdInfoDto> getListData(@RequestBody TbFnncJntCdInfoDto inputDto, HttpServletRequest request) throws Exception {
        log.debug(BizUtils.logInfo(BizUtils.logVoKey(inputDto)));
        return jntCdService.getListData(inputDto);
    }

    /**
     * @ ID : getJntCdData
     * @ NAME : 금융공동코드 상세 조회
     */
    @PostMapping("/getJntCdData")
    @ResponseBody
    public TbFnncJntCdInfoDto getJntCdData(@RequestBody TbFnncJntCdInfoDto inputDto, HttpServletRequest request) throws Exception {
        log.debug(BizUtils.logInfo());
        return jntCdService.getData(inputDto);
    }

    /**
     * @ ID : manageJntCdData
     * @ NAME : 금융공동코드 관리
     */
    @PostMapping("/manageJntCdData")
    @ResponseBody
    public  ApiResponse<Void> manageJntCdData(@RequestBody TbFnncJntCdInfoDto inputDto, HttpServletRequest request) throws Exception {
        log.debug(BizUtils.logInfo());
        jntCdService.manageData(inputDto);
        return ApiResponse.success(null);
    }

    /**
     * @ ID : uploadJntCdPreview
     * @ NAME : 금융공동코드 엑셀업로드
     */
    @PostMapping("/uploadJntCdPreview")
    public ResponseEntity<Map<String, Object>> uploadJntCdPreview(@RequestParam("file") MultipartFile file) {
        log.debug(BizUtils.logInfo());
        Map<String, Object> res = new HashMap<>();
        try {
            List<TbFnncJntCdInfoDto> list = jntCdService.uploadPreview(file);
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
     * @ NAME : 금융공동코드 엑셀업로드 저장
     */
    @PostMapping("/saveJntCdUploaded")
    public ResponseEntity<Map<String, Object>> saveUploaded(@RequestBody List<TbFnncJntCdInfoDto> list, HttpServletRequest request) {
        log.debug(BizUtils.logInfo());
        Map<String, Object> res = new HashMap<>();
        try {
            int count = jntCdService.saveUploaded(list);
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
