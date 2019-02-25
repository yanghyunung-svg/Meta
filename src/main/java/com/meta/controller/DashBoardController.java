package com.meta.controller;

import com.common.utils.BizUtils;
import com.meta.dto.DashBoardDto;
import com.meta.service.DashBoardService;
import jakarta.servlet.http.HttpSession;
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
public class DashBoardController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DashBoardService dashBoardService;

    /**
     * @ID : getDashBoardListData
     * @NAME : DashBoard 조회
     */
    @PostMapping("/getDashBoardList")
    @ResponseBody
    public List<DashBoardDto> getDashBoardList(@RequestBody DashBoardDto inputDto, HttpSession session) throws Exception {
        log.debug(BizUtils.logInfo("START"));

//        String userId = (String) session.getAttribute("userId");
//        inputDto.setCrtId(userId);
        inputDto.setStat("0");

        List<DashBoardDto> outputDto =  dashBoardService.getListData(inputDto);
        log.debug(BizUtils.logInfo("END"));

        return outputDto;
    }

    /**
     * @ID : getDashBoardData
     * @NAME : DashBoard 상세 조회
     */
    @PostMapping("/getDashBoardData")
    @ResponseBody
    public DashBoardDto getDashBoardData(@RequestBody DashBoardDto inputDto, HttpSession session) throws Exception {
        log.debug(BizUtils.logInfo("START"));
        return dashBoardService.getData(inputDto);
    }

}
