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
    private TbWordDictionaryMapper tbWordDictionaryMapper;
    @Autowired
    private TbTermDictionaryMapper tbTermDictionaryMapper;
    @Autowired
    private TbStdDmnBscMapper tbStdDmnBscMapper;
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
            TbTermDictionaryDto termIn = new TbTermDictionaryDto();
            termIn.setTrmNm(inputDto.getKorNm());
            TbTermDictionaryDto termOut = tbTermDictionaryMapper.getLockData(termIn);
            if (termOut != null) {
                termOut.setStat(inputDto.getStat());
                termOut.setUpdId(inputDto.getUserId());
                tbTermDictionaryMapper.updateData(termOut);
            }
            break;
        case "2":
            TbWordDictionaryDto wordIn = new TbWordDictionaryDto();
            wordIn.setId(inputDto.getId());
            TbWordDictionaryDto wordOut = tbWordDictionaryMapper.getLockData(wordIn);
            if (wordOut != null) {
                wordOut.setStat(inputDto.getStat());
                wordOut.setUpdId(inputDto.getUserId());
                tbWordDictionaryMapper.updateData(wordOut);
            }
            break;
        case "3":
            TbStdDmnBscDto dmnIn = new TbStdDmnBscDto();
            dmnIn.setDmnNm(inputDto.getKorNm());
            TbStdDmnBscDto dmnOut = tbStdDmnBscMapper.getLockData(dmnIn);
            if (dmnOut != null) {
                dmnOut.setSttsCd(inputDto.getStat());
                dmnOut.setUpdId(inputDto.getUserId());
                tbStdDmnBscMapper.updateData(dmnOut);
            }
            break;
        case "4":
            TbCodeGroupDto codeIn = new TbCodeGroupDto();
            codeIn.setGrpCd(inputDto.getKorNm());
            TbCodeGroupDto codeOut = tbCodeGroupMapper.getLockData(codeIn);
            if (codeOut != null) {
                codeOut.setStat(inputDto.getStat());
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

