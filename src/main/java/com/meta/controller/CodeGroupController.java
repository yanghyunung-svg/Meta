package com.meta.controller;

import com.common.utils.ApiResponse;
import com.common.utils.BizUtils;
import com.meta.dto.TbCodeGroupDto;
import com.meta.service.CodeGroupService;
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
public class CodeGroupController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CodeGroupService codeGroupService;
 
    /**
     * @ID : getCodeGroupListData
     * @NAME : 코드그룹 목록 조회
     */
    @PostMapping("/getCodeGroupListData")
    @ResponseBody
    public List<TbCodeGroupDto> getCodeGroupListData(@RequestBody TbCodeGroupDto inputDto, HttpSession session) throws Exception {
        log.debug(BizUtils.logInfo("START"));
        return codeGroupService.getListData(inputDto);
    }

    /**
     * @ID : getCodeGroupData
     * @NAME : 코드그룹 상세 조회
     */
    @PostMapping("/getCodeGroupData")
    @ResponseBody
    public TbCodeGroupDto getCodeGroupData(@RequestBody TbCodeGroupDto inputDto, HttpSession session) throws Exception {
        log.debug(BizUtils.logInfo("START"));
        return codeGroupService.getData(inputDto);
    }

    /**
     * @ID : insertCodeGroupData
     * @NAME : 코드그룹 등록
     */
    @PostMapping("/insertCodeGroupData")
    @ResponseBody
    public ApiResponse<Void> insertCodeGroupData(@RequestBody TbCodeGroupDto inputDto, HttpSession session) throws Exception {
        log.debug(BizUtils.logInfo("START", BizUtils.logVoKey(inputDto)));

        String userId = (String) session.getAttribute("userId");
        inputDto.setCrtId(userId);
        inputDto.setUpdId(userId);
        ApiResponse<Void> outputDto = codeGroupService.insertData(inputDto);

        log.debug(BizUtils.logInfo("END", BizUtils.logVo(outputDto)));
        return outputDto;
    }
    /**
     * @ID : updateCodeGroupData
     * @NAME : 코드그룹 변경
     */
    @PostMapping("/updateCodeGroupData")
    @ResponseBody
    public ApiResponse<Void> updateCodeGroupData(@RequestBody TbCodeGroupDto inputDto, HttpSession session) throws Exception {
        log.debug(BizUtils.logInfo("START", BizUtils.logVoKey(inputDto)));

        String userId = (String) session.getAttribute("userId");
        inputDto.setCrtId(userId);
        inputDto.setUpdId(userId);
        ApiResponse<Void> outputDto = codeGroupService.updateData(inputDto);

        log.debug(BizUtils.logInfo("END", BizUtils.logVo(outputDto)));
        return outputDto;
    }
}
