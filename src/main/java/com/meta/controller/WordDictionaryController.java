package com.meta.controller;

import com.common.utils.ApiResponse;
import com.common.utils.BizUtils;
import com.meta.dto.TbWordDictionaryDto;
import com.meta.service.WordDictionaryService;
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
public class WordDictionaryController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private WordDictionaryService wordDictionaryService;

    /**
     * @ID : getWordListData
     * @NAME : 단어사전 목록 조회
     */
    @PostMapping("/getWordListData")
    @ResponseBody
    public List<TbWordDictionaryDto> getWordListData(@RequestBody TbWordDictionaryDto inputDto, HttpSession session) throws Exception {
        log.debug(BizUtils.logInfo("START"));
        return wordDictionaryService.getListData(inputDto);
    }

    /**
     * @ID : getWordData
     * @NAME : 단어사전 상세 조회
     */
    @PostMapping("/getWordData")
    @ResponseBody
    public TbWordDictionaryDto getWordData(@RequestBody TbWordDictionaryDto inputDto, HttpSession session) throws Exception {
        log.debug(BizUtils.logInfo("START"));
        return wordDictionaryService.getData(inputDto);
    }

    /**
     * @ID : insertWordData
     * @NAME : 단어사전 등록
     */
    @PostMapping("/insertWordData")
    @ResponseBody
    public ApiResponse<Void> insertWordData(@RequestBody TbWordDictionaryDto inputDto, HttpSession session) throws Exception {
        log.debug(BizUtils.logInfo("START", BizUtils.logVo(inputDto)));

        String userId = (String) session.getAttribute("userId");
        inputDto.setCrtId(userId);
        inputDto.setUpdId(userId);

        ApiResponse<Void> outputDto = wordDictionaryService.insertData(inputDto);

        log.debug(BizUtils.logInfo("END", BizUtils.logVo(outputDto)));
        return outputDto;
    }

    /**
     * @ID : updateWordData
     * @NAME : 단어사전 변경
     */
    @PostMapping("/updateWordData")
    @ResponseBody
    public ApiResponse<Void> updateWordData(@RequestBody TbWordDictionaryDto inputDto, HttpSession session) throws Exception {
        log.debug(BizUtils.logInfo("START", BizUtils.logVo(inputDto)));

        String userId = (String) session.getAttribute("userId");
        inputDto.setCrtId(userId);
        inputDto.setUpdId(userId);

        ApiResponse<Void> outputDto = wordDictionaryService.updateData(inputDto);

        log.debug(BizUtils.logInfo("END"));
        return outputDto;
    }
}
