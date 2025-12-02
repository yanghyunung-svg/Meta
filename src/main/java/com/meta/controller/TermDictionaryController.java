package com.meta.controller;

import com.common.utils.ApiResponse;
import com.common.utils.BizUtils;
import com.meta.dto.TbTermDictionaryDto;
import com.meta.service.TermDictionaryService;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Data
@Controller
@RequestMapping("/meta")
public class TermDictionaryController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TermDictionaryService termDictionaryService;

    /**
     * @ID : getTermListData
     * @NAME : 용어사전 목록 조회
     */
    @PostMapping("/getTermListData")
    @ResponseBody
    public List<TbTermDictionaryDto> getTermListData(@RequestBody TbTermDictionaryDto inputDto, HttpSession session) throws Exception {
        log.debug(BizUtils.logInfo("START"));
        return termDictionaryService.getListData(inputDto);
    }

    /**
     * @ID : getTermData
     * @NAME : 용어사전 상세 조회
     */
    @PostMapping("/getTermData")
    @ResponseBody
    public TbTermDictionaryDto getTermData(@RequestBody TbTermDictionaryDto inputDto, HttpSession session) throws Exception {
        log.debug(BizUtils.logInfo("START"));
        return termDictionaryService.getData(inputDto);
    }

    /**
     * @ID : insertTermData
     * @NAME : 용어사전 등록
     */
    @PostMapping("/insertTermData")
    @ResponseBody
    public ApiResponse insertTermData(@RequestBody TbTermDictionaryDto inputDto, HttpSession session) throws Exception {
        log.debug(BizUtils.logInfo("START", BizUtils.logVo(inputDto)));
        String userId = (String) session.getAttribute("userId");
        inputDto.setCrtId(userId);
        inputDto.setUpdId(userId);
        ApiResponse outputDto = termDictionaryService.insertData(inputDto);
        log.debug(BizUtils.logInfo("END", BizUtils.logVo(outputDto)));
        return outputDto;
    }

    /**
     * @ID : updateTermData
     * @NAME : 용어사전 등록
     */
    @PostMapping("/updateTermData")
    @ResponseBody
    public ApiResponse updateTermData(@RequestBody TbTermDictionaryDto inputDto, HttpSession session) throws Exception {
        log.debug(BizUtils.logInfo("START", BizUtils.logVo(inputDto)));
        String userId = (String) session.getAttribute("userId");
        inputDto.setCrtId(userId);
        inputDto.setUpdId(userId);
        ApiResponse outputDto = termDictionaryService.updateData(inputDto);
        log.debug(BizUtils.logInfo("END", BizUtils.logVo(outputDto)));
        return outputDto;
    }
}
