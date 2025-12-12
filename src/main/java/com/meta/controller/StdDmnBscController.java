package com.meta.controller;

import com.common.utils.ApiResponse;
import com.common.utils.BizUtils;
import com.meta.dto.TbStdDmnBscDto;
import com.meta.service.StdDmnBscService;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Data
@Controller
@RequestMapping("/meta")
public class StdDmnBscController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private StdDmnBscService stdDmnBscService;

    /**
     * @ID : getDmnListData
     * @NAME : 표준도메인 목록 조회
     */
    @PostMapping("/getDmnListData")
    @ResponseBody
    public List<TbStdDmnBscDto> getDmnListData(@RequestBody TbStdDmnBscDto inputDto, HttpSession session) throws Exception {
        log.debug(BizUtils.logInfo("START"));
        return stdDmnBscService.getListData(inputDto);
    }
    /**
     * @ID : getDmnComboData
     * @NAME : 표준도메인 콤보 조회
     */
    @PostMapping("/getDmnComboData")
    public ResponseEntity<List<TbStdDmnBscDto>> getDmnComboData()  {
        List<TbStdDmnBscDto> outputDto = stdDmnBscService.getDmnComboData();
        return ResponseEntity.ok(outputDto);
    }

    /**
     * @ID : getDmnData
     * @NAME : 표준도메인 상세 조회
     */
    @PostMapping("/getDmnData")
    @ResponseBody
    public TbStdDmnBscDto getDmnData(@RequestBody TbStdDmnBscDto inputDto, HttpSession session) throws Exception {
        log.debug(BizUtils.logInfo("START"));
        return stdDmnBscService.getData(inputDto);
    }

    /**
     * @ID : insertDmnData
     * @NAME : 표준도메인 등록
     */
    @PostMapping("/insertDmnData")
    @ResponseBody
    public ApiResponse<Void> insertDmnData(@RequestBody TbStdDmnBscDto inputDto, HttpSession session) throws Exception {
        log.debug(BizUtils.logInfo("START", BizUtils.logVo(inputDto)));
        ApiResponse<Void> outputDto = stdDmnBscService.insertData(inputDto);
        log.debug(BizUtils.logInfo("END", BizUtils.logVo(outputDto)));
        return outputDto;
    }

    /**
     * @ID : updateDmnData
     * @NAME : 표준도메인 변경
     */
    @PostMapping("/updateDmnData")
    @ResponseBody
    public ApiResponse<Void> updateDmnData(@RequestBody TbStdDmnBscDto inputDto, HttpSession session) throws Exception {
        log.debug(BizUtils.logInfo("START", BizUtils.logVo(inputDto)));
        ApiResponse<Void> outputDto = stdDmnBscService.updateData(inputDto);
        log.debug(BizUtils.logInfo("END", BizUtils.logVo(outputDto)));
        return outputDto;
    }

}
