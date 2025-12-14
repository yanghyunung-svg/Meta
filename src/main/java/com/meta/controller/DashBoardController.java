package com.meta.controller;

import com.common.utils.BizUtils;
import com.meta.dto.ApproveDto;
import com.meta.service.DashBoardService;
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
public class DashBoardController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DashBoardService dashBoardService;

    /**
     * @ ID : dashboardView
     * @ NAME : DashBoard PAGE
     */
    @GetMapping("/dashboard")
    public String dashboardView(Model model) { return "meta/dashboard"; }

    /**
     * @ ID : getDashBoardListData
     * @ NAME : DashBoard 조회
     */
    @PostMapping("/getDashBoardList")
    @ResponseBody
    public List<ApproveDto> getDashBoardList(@RequestBody ApproveDto inputDto, HttpSession session) throws Exception {
        log.debug(BizUtils.logInfo("START"));
        inputDto.setStat("0");
        List<ApproveDto> outputDto =  dashBoardService.getListData(inputDto);
        log.debug(BizUtils.logInfo("END"));
        return outputDto;
    }


}
