package com.meta.controller;

import com.common.utils.BizUtils;
import com.meta.dto.ApproveDto;
import com.meta.service.ApproveService;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@Controller
@RequestMapping("/meta")
public class ApproveController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ApproveService approveService;

    /**
     * @ID : aprvDsctnInqView
     * @NAME : 용어단어 승인내역 조회
     */
    @GetMapping("/aprvDsctnInq")
    public String aprvDsctnInqView(Model model) { return "meta/aprvDsctnInq"; }

    /**
     * @ID : getAprvDsctnList
     * @NAME : 승인내역 조회
     */
    @PostMapping("/getAprvDsctnList")
    @ResponseBody
    public List<ApproveDto> getAprvDsctnList(@RequestBody ApproveDto inputDto, HttpSession session) throws Exception {
        log.debug(BizUtils.logInfo("START"));
        List<ApproveDto> outputDto =  approveService.getListData(inputDto);
        log.debug(BizUtils.logInfo("END"));
        return outputDto;
    }
    /**
     * @ID : prcsAprvDsctn
     * @NAME : 승인처리
     */
    @PostMapping("/prcsAprvDsctn")
    @ResponseBody
    public ApproveDto prcsAprvDsctn(@RequestBody ApproveDto inputDto, HttpSession session) throws Exception {
        log.debug(BizUtils.logInfo("START"));
        ApproveDto outputDto =  approveService.prcsAprvDsctn(inputDto);
        log.debug(BizUtils.logInfo("END"));
        return outputDto;
    }

}
