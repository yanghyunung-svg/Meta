package com.meta.controller;

import com.common.utils.BizUtils;
import com.meta.dto.ApproveDto;
import com.meta.service.ApproveService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Data
@Controller
@RequestMapping("/meta")
public class ApproveController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ApproveService approveService;

    /**
     * @ ID : getAprvDsctnList
     * @ NAME : 신청내역 조회
     */
    @PostMapping("/getAplyDsctnList")
    @ResponseBody
    public List<ApproveDto> getAplyDsctnList(@RequestBody ApproveDto inputDto, HttpServletRequest request) throws Exception {
        log.debug(BizUtils.logInfo("START"));
        String userId = (String) request.getSession().getAttribute("userId");
        inputDto.setStat("0");
        log.debug(BizUtils.logInfo("userId", userId));
        List<ApproveDto> outputDto =  approveService.getListData(inputDto);
        log.debug(BizUtils.logInfo("END"));
        return outputDto;
    }

    /**
     * @ ID : getAprvDsctnList
     * @ NAME : 승인내역 조회
     */
    @PostMapping("/getAprvDsctnList")
    @ResponseBody
    public List<ApproveDto> getAprvDsctnList(@RequestBody ApproveDto inputDto, HttpServletRequest request) throws Exception {
        log.debug(BizUtils.logInfo("START"));
        String userId = (String) request.getSession().getAttribute("userId");
        log.debug(BizUtils.logInfo("userId", userId));
        List<ApproveDto> outputDto =  approveService.getListData(inputDto);
        log.debug(BizUtils.logInfo("END"));
        return outputDto;
    }
    /**
     * @ ID : prcsAprvDsctn
     * @ NAME : 승인처리
     */
    @PostMapping("/prcsAprvDsctn")
    @ResponseBody
    public ApproveDto prcsAprvDsctn(@RequestBody ApproveDto inputDto, HttpServletRequest request) throws Exception {
        log.debug(BizUtils.logInfo("START"));

        String userId = (String) request.getSession().getAttribute("userId");
        log.debug(BizUtils.logInfo("userId", userId));
        ApproveDto outputDto =  approveService.prcsAprvDsctn(inputDto);
        log.debug(BizUtils.logInfo("END"));
        return outputDto;
    }

}
