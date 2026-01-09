package com.meta.controller;

import com.meta.dto.CommCodeDto;
import com.meta.service.CommService;
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
public class CommController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CommService commService;
 
    /**
     * @ ID : getCommCodeSearch
     * @ NAME : 공통코드 통합 검색
     */
    @PostMapping("/getCommCodeSearch")
    @ResponseBody
    public List<CommCodeDto> getCommCodeSearch(@RequestBody CommCodeDto inputDto, HttpServletRequest request) throws Exception {
        return commService.getCommCodeSearch(inputDto);
    }


}
