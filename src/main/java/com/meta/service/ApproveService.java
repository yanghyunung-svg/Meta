package com.meta.service;

import com.meta.dto.*;
import com.meta.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/** 
 *@ ID       : ApproveService
 *@ NAME     : 승인 Service
 */
@Service
public class ApproveService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ApproveMapper approveMapper;
    @Autowired
    private TbStdWordBscMapper wordMapper;
    @Autowired
    private TbStdTermBscMapper termMapper;
    @Autowired
    private TbStdDmnBscMapper dmnMapper;
    @Autowired
    private TbCodeGroupMapper tbCodeGroupMapper;


    /**
     * @ ID   : getListData
     * @ NAME     : 목록 조회
     */
    public List<ApproveDto> getListData(ApproveDto inputDto)  {
        return approveMapper.getListData(inputDto);
    }
    /**
     * @ ID   : prcsAprvDsctn
     * @ NAME     : 상태변경처리
     */
    public ApproveDto prcsAprvDsctn(ApproveDto inputDto)  {
        ApproveDto outputDto = new ApproveDto();
        switch (inputDto.getSe()) {
        case "1":
            TbStdTermBscDto termIn = new TbStdTermBscDto();
            termIn.setTrmNm(inputDto.getKorNm());
            TbStdTermBscDto termOut = termMapper.getLockData(termIn);
            if (termOut != null) {
                termOut.setSttsCd(inputDto.getSttsCd());
                termOut.setUpdId(inputDto.getUserId());
                termMapper.updateData(termOut);
            }
            break;
        case "2":
            TbStdWordBscDto wordIn = new TbStdWordBscDto();
            wordIn.setId(inputDto.getId());
            TbStdWordBscDto wordOut = wordMapper.getLockData(wordIn);
            if (wordOut != null) {
                wordOut.setSttsCd(inputDto.getSttsCd());
                wordOut.setUpdId(inputDto.getUserId());
                wordMapper.updateData(wordOut);
            }
            break;
        case "3":
            TbStdDmnBscDto dmnIn = new TbStdDmnBscDto();
            dmnIn.setDmnNm(inputDto.getKorNm());
            TbStdDmnBscDto dmnOut = dmnMapper.getLockData(dmnIn);
            if (dmnOut != null) {
                dmnOut.setSttsCd(inputDto.getSttsCd());
                dmnOut.setUpdId(inputDto.getUserId());
                dmnMapper.updateData(dmnOut);
            }
            break;
        case "4":
            TbCodeGroupDto codeIn = new TbCodeGroupDto();
            codeIn.setGrpCd(inputDto.getKorNm());
            TbCodeGroupDto codeOut = tbCodeGroupMapper.getLockData(codeIn);
            if (codeOut != null) {
                codeOut.setSttsCd(inputDto.getSttsCd());
                codeOut.setUpdId(inputDto.getUserId());
                tbCodeGroupMapper.updateData(codeOut);
            }
            break;
        default:
            break;
        }
        return outputDto;
    }

}

