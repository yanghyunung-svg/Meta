package com.meta.controller;

import com.meta.common.response.ApiResponse;
import com.meta.dto.TbTelgmInterfaceBscDto;
import com.meta.service.TelgmInterfaceService;
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
public class TelgmInterfaceController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TelgmInterfaceService telgmInterfaceService;

    @GetMapping("/telgm/telgmInterfaceList")
    public String telgmInterfaceListPage() { return "meta/telgm/telgmInterfaceList"; }
    @GetMapping("/telgm/telgmInterfaceMng")
    public String telgmInterfaceMngPage() { return "meta/telgm/telgmInterfaceMng"; }
    @GetMapping("/telgm/telgmInterfaceRegEblc")
    public String telgmRegKndEblcPage() { return "meta/telgm/telgmInterfaceRegEblc"; }

    /**
     * @ ID : getTelgmInterfaceListData
     * @ NAME : 전문인터페이스 목록 조회
     */
    @PostMapping("/getTelgmInterfaceListData")
    @ResponseBody
    public List<TbTelgmInterfaceBscDto> getTelgmInterfaceListData(@RequestBody TbTelgmInterfaceBscDto inputDto, HttpServletRequest request) throws Exception {
        return telgmInterfaceService.getListData(inputDto);
    }

    /**
     * @ ID : getTelgmInterfaceData
     * @ NAME : 전문인터페이스 상세 조회
     */
    @PostMapping("/getTelgmInterfaceData")
    @ResponseBody
    public TbTelgmInterfaceBscDto getTelgmInterfaceData(@RequestBody TbTelgmInterfaceBscDto inputDto, HttpServletRequest request) throws Exception {
        return telgmInterfaceService.getData(inputDto);
    }

    /**
     * @ ID : manageTelgmInterfaceData
     * @ NAME : 전문인터페이스 관리
     */
    @PostMapping("/manageTelgmInterfaceData")
    @ResponseBody
    public  ApiResponse<Void> manageTelgmInterfaceData(@RequestBody TbTelgmInterfaceBscDto inputDto, HttpServletRequest request) throws Exception {
        telgmInterfaceService.manageData(inputDto);
        return ApiResponse.success(null);
    }

    /**
     * @ ID : uploadTelgmInterfacePreview
     * @ NAME : 전문인터페이스 엑셀업로드
     */
    @PostMapping("/uploadTelgmInterfacePreview")
    public ResponseEntity<Map<String, Object>> uploadTelgmInterfacePreview(@RequestParam("file") MultipartFile file) {
        Map<String, Object> res = new HashMap<>();
        try {
            List<TbTelgmInterfaceBscDto> list = telgmInterfaceService.uploadPreview(file);
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
     * @ ID : saveTelgmInterfaceUploaded
     * @ NAME : 전문인터페이스 엑셀업로드 저장
     */
    @PostMapping("/saveTelgmInterfaceUploaded")
    public ResponseEntity<Map<String, Object>> saveTelgmInterfaceUploaded(@RequestBody List<TbTelgmInterfaceBscDto> list, HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        try {
            int count = telgmInterfaceService.saveUploaded(list);
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
