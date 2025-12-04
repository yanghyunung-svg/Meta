package com.meta.service;

import ch.qos.logback.core.util.StringUtil;
import com.common.utils.ApiResponse;
import com.common.utils.BizUtils;
import com.meta.dto.TbTermDictionaryDto;
import com.meta.dto.TbWordDictionaryDto;
import com.meta.dto.WordMappingDto;
import com.meta.mapper.TbTermDictionaryMapper;
import com.meta.mapper.TbWordDictionaryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/** 
 *@ID       : TermDictionaryService
 *@NAME     : 용어사전 Service
 */
@Service
public class TermDictionaryService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TbTermDictionaryMapper tbTermDictionaryMapper;
    @Autowired
    private TbWordDictionaryMapper tbWordDictionaryMapper;


    /**
     * method   : getListData
     * desc     : 용어사전 목록 조회
     */
    public List<TbTermDictionaryDto> getListData(TbTermDictionaryDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        return tbTermDictionaryMapper.getListData(inputDto);
    }

    /**
     * method   : getData
     * desc     : 용어사전 상세 조회
     */
    public TbTermDictionaryDto getData(TbTermDictionaryDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        return tbTermDictionaryMapper.getData(inputDto);
    }
    /**
     * method   : insertData
     * desc     : 용어사전 등록
     */
    public ApiResponse insertData(TbTermDictionaryDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        log.debug(BizUtils.logVo(inputDto));
        try {
            log.debug(BizUtils.logInfo("SELECT", inputDto.getTrmNm()));
            TbTermDictionaryDto outputDto = tbTermDictionaryMapper.getDataByName(inputDto);

            if (outputDto != null) {
                log.debug(BizUtils.logInfo("기등록된 데이타가 있습니다"));
                return new ApiResponse<Void>(false, "기등록된 데이타가 있습니다." + outputDto.getEngNm());
            }

            if (tbTermDictionaryMapper.insertData(inputDto) == 0) {
                return new ApiResponse<Void>(false, "등록 오류");
            }

            return new ApiResponse<Void>(true, "등록 성공");

        } catch (Exception e) {
            log.debug(BizUtils.logInfo("Exception"));
            return new ApiResponse<Void>(false, "등록 처리 중 오류가 발생했습니다.");
        }
    }

    /**
     * method   : updateData
     * desc     : 용어사전 변경
     */
    public ApiResponse<Void> updateData(TbTermDictionaryDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        log.debug(BizUtils.logVo(inputDto));

        try {
            TbTermDictionaryDto outputDto = tbTermDictionaryMapper.getData(inputDto);

            if (outputDto == null) {
                return new ApiResponse<Void>(false, "수정할 데이타가 없습니다");
            }

            if (tbTermDictionaryMapper.updateData(inputDto) == 0) {
                return new ApiResponse<Void>(false, "수정 오류");
            }

            log.debug(BizUtils.logVoKey(outputDto));
            return new ApiResponse<Void>(true, "수정 성공");

        } catch (Exception e) {
            return new ApiResponse<Void>(false, "수정 처리 중 오류가 발생했습니다.");
        }
    }

    /**
     * method   : getTermSplitData
     * desc     : 용어사전 단어목록 조회
     */
    public TbTermDictionaryDto getTermSplitData(TbTermDictionaryDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        log.debug(BizUtils.logVo(inputDto));

        TbTermDictionaryDto tbTermDictionaryDto = tbTermDictionaryMapper.getDataByName(inputDto);
        if (tbTermDictionaryDto != null) {
            return tbTermDictionaryDto;
        }

        TbTermDictionaryDto outputDto = new TbTermDictionaryDto();
        String inText = inputDto.getTrmNm().replace(" ", "");

        // 1. 단어 테이블에 있는 단어로 자동 분리
        List<WordMappingDto> keywords = extractKeywords(inText);

        // 2. DB 조회 및 Snake Case 변환 실행
        String outTxt = translateToSnakeCase(keywords);

        outputDto.setTrmNm(inText);
        outputDto.setEngNm(outTxt);
        outputDto.setTrmExpln(prettyPrintKeywords(keywords));

        log.debug(BizUtils.logInfo("END"));
        return outputDto;
    }

    public List<WordMappingDto> extractKeywords(String compoundWord) {
        List<WordMappingDto> keywords = new ArrayList<>();
        int length = compoundWord.length();
        int currentIndex = 0;
        while (currentIndex < length) {
            boolean found = false;
            for (int end = length; end > currentIndex; end--) {
                String inText = compoundWord.substring(currentIndex, end);
                String outText = this.getWordDta(inText);
                if(!StringUtil.isNullOrEmpty(outText)) {
                    WordMappingDto dto = new WordMappingDto();
                    dto.setKorNm(inText);
                    dto.setEngNm(outText);
                    keywords.add(dto);
                    currentIndex = end;
                    found = true;
                    break;
                }
            }
            if (!found) {
                String single = compoundWord.substring(currentIndex, currentIndex + 1);
                WordMappingDto dto = new WordMappingDto();
                dto.setKorNm(single);
                dto.setEngNm(""); // 또는 null
                keywords.add(dto);
                currentIndex++;
            }
        }
        return keywords;
    }

    private String translateToSnakeCase(List<WordMappingDto> keywords) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keywords.size(); i++) {
            WordMappingDto dto = keywords.get(i);
            String englishWord = dto.getEngNm();
            if (englishWord != null && !englishWord.isEmpty()) {
                sb.append(englishWord.toUpperCase());
            } else {
                sb.append(dto.getKorNm().toUpperCase());
            }
            if (i < keywords.size() - 1) {
                sb.append("_");
            }
        }
        return sb.toString();
    }


    private String getWordDta(String wordNm)  {
        TbWordDictionaryDto wordDto = new TbWordDictionaryDto();
        wordDto.setWordNm(wordNm);
        wordDto = tbWordDictionaryMapper.getDataByName(wordDto);
        return wordDto == null  ?  "" :  wordDto.getEngAbbrNm();
    }

    private String prettyPrintKeywords(List<WordMappingDto> keywords) {
        StringBuilder sb = new StringBuilder();
        for (WordMappingDto dto : keywords) {
            sb.append(dto.getKorNm());
            sb.append(" = ");
            sb.append(dto.getEngNm() != null ? dto.getEngNm() : "");
            sb.append("\n");
        }
        return sb.toString().trim();
    }
}

