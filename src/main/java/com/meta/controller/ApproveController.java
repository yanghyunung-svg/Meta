package com.meta.controller;

import com.meta.common.util.BizUtils;
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
        String userId = (String) request.getSession().getAttribute("userId");
        inputDto.setStat("0");
        return approveService.getListData(inputDto);
    }

    /**
     * @ ID : getAprvDsctnList
     * @ NAME : 승인내역 조회
     */
    @PostMapping("/getAprvDsctnList")
    @ResponseBody
    public List<ApproveDto> getAprvDsctnList(@RequestBody ApproveDto inputDto, HttpServletRequest request) throws Exception {

        String userId = (String) request.getSession().getAttribute("userId");
        log.debug(BizUtils.logInfo("userId", userId));


        return  approveService.getListData(inputDto);
    }
    /**
     * @ ID : prcsAprvDsctn
     * @ NAME : 승인처리
     */
    @PostMapping("/prcsAprvDsctn")
    @ResponseBody
    public ApproveDto prcsAprvDsctn(@RequestBody ApproveDto inputDto, HttpServletRequest request) throws Exception {
        String userId = (String) request.getSession().getAttribute("userId");
        return  approveService.prcsAprvDsctn(inputDto);
    }

}
