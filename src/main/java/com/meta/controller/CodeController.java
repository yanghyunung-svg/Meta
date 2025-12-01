package com.meta.controller;

import com.common.utils.ApiResponse;
import com.common.utils.BizUtils;
import com.meta.dto.TbCodeDto;
import com.meta.service.CodeService;
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
public class CodeController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CodeService codeService;
 
    /**
     * @ID : getCodeListData
     * @NAME : 코드 목록 조회
     */
    @PostMapping("/getCodeListData")
    @ResponseBody
    public List<TbCodeDto> getCodeListData(@RequestBody TbCodeDto inputDto, HttpSession session) throws Exception {
        log.debug(BizUtils.logInfo("START"));
        return codeService.getListData(inputDto);
    }

    /**
     * @ID : getCodeData
     * @NAME : 코드 상세 조회
     */
    @PostMapping("/getCodeData")
    @ResponseBody
    public TbCodeDto getCodeData(@RequestBody TbCodeDto inputDto, HttpSession session) throws Exception {
        log.debug(BizUtils.logInfo("START"));
        return codeService.getData(inputDto);
    }

    /**
     * @ID : insertCodeData
     * @NAME : 코드 등록
     */
    @PostMapping("/insertCodeData")
    @ResponseBody
    public ApiResponse<Void> insertCodeData(@RequestBody TbCodeDto inputDto, HttpSession session) throws Exception {
        log.debug(BizUtils.logInfo("START", BizUtils.logVoKey(inputDto)));

        String userId = (String) session.getAttribute("userId");
        inputDto.setCrtId(userId);
        inputDto.setUpdId(userId);
        ApiResponse<Void> outputDto = codeService.insertData(inputDto);

        log.debug(BizUtils.logInfo("END", BizUtils.logVo(outputDto)));
        return outputDto;
    }
    /**
     * @ID : updateCodeData
     * @NAME : 코드 변경
     */
    @PostMapping("/updateCodeData")
    @ResponseBody
    public ApiResponse<Void> updateCodeData(@RequestBody TbCodeDto inputDto, HttpSession session) throws Exception {
        log.debug(BizUtils.logInfo("START", BizUtils.logVoKey(inputDto)));

        String userId = (String) session.getAttribute("userId");
        inputDto.setCrtId(userId);
        inputDto.setUpdId(userId);
        ApiResponse<Void> outputDto = codeService.updateData(inputDto);

        log.debug(BizUtils.logInfo("END", BizUtils.logVo(outputDto)));
        return outputDto;
    }
}
