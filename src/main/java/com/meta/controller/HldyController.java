package com.meta.controller;

import com.meta.common.response.ApiResponse;
import com.meta.dto.TbHldyInfDto;
import com.meta.service.HldyService;
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
public class HldyController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private HldyService hldyService;

    /* 휴일정보 */
    @GetMapping("/hldy/hldyList")
    public String hldyListPage() { return "meta/hldy/hldyList"; }

    @GetMapping("/hldy/hldyMng")
    public String hldyMngPage() { return "meta/hldy/hldyMng"; }

    @GetMapping("/hldy/hldyRegEblc")
    public String hldyRegEblcPage() { return "meta/hldy/hldyRegEblc"; }

    /**
     * @ ID : getHldyListData
     * @ NAME : 코드그룹 목록 조회
     */
    @PostMapping("/getHldyListData")
    @ResponseBody
    public List<TbHldyInfDto> getHldyListData(@RequestBody TbHldyInfDto inputDto, HttpServletRequest request) throws Exception {
        return hldyService.getListData(inputDto);
    }

    /**
     * @ ID : getHldyData
     * @ NAME : 코드그룹 상세 조회
     */
    @PostMapping("/getHldyData")
    @ResponseBody
    public TbHldyInfDto getHldyData(@RequestBody TbHldyInfDto inputDto, HttpServletRequest request) throws Exception {
        return hldyService.getData(inputDto);
    }

    /**
     * @ ID : managerHldyData
     * @ NAME : 코드그룹 관리
     */
    @PostMapping("/manageHldyData")
    @ResponseBody
    public ApiResponse<Void> manageHldyData(@RequestBody TbHldyInfDto inputDto, HttpServletRequest request) throws Exception {
        hldyService.manageData(inputDto);
        return ApiResponse.success(null);
    }
    /**
     * @ ID : uploadCodePreview
     * @ NAME : 코드그룹 엑셀업로드
     */
    @PostMapping("/uploadHldyPreview")
    public ResponseEntity<Map<String, Object>> uploadHldyPreview(@RequestParam("file") MultipartFile file) {
        Map<String, Object> res = new HashMap<>();
        try {
            List<TbHldyInfDto> list = hldyService.parsePreview(file);
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
     * @ ID : uploadCodeExcelSave
     * @ NAME : 코드그룹 엑셀업로드 저장
     */
    @PostMapping("/uploadHldyExcelSave")
    public ResponseEntity<Map<String, Object>> uploadHldyExcelSave(@RequestBody List<TbHldyInfDto> list) {
        Map<String, Object> res = new HashMap<>();
        try {
            int count = hldyService.saveUploaded(list);
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
