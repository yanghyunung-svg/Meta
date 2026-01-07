package com.meta.service;

import ch.qos.logback.core.util.StringUtil;
import com.meta.common.constants.BizConstants;
import com.meta.common.constants.ResponseCode;
import com.meta.common.exception.BizException;
import com.meta.common.util.BizUtils;
import com.meta.dto.TbStdTermBscDto;
import com.meta.dto.TbStdWordBscDto;
import com.meta.dto.WordMappingDto;
import com.meta.mapper.TbStdTermBscMapper;
import com.meta.mapper.TbStdWordBscMapper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/** 
 *@ ID       : StdTermBscService
 *@ NAME     : 용어사전 Service
 */
@Service
public class StdTermBscService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TbStdTermBscMapper termMapper;
    @Autowired
    private TbStdWordBscMapper wordMapper;

    /**
     * method   : getListData
     * desc     : 용어사전 목록 조회
     */
    public List<TbStdTermBscDto> getListData(TbStdTermBscDto inputDto)  {
        return termMapper.getListData(inputDto);
    }

    /**
     * method   : getData
     * desc     : 용어사전 상세 조회
     */
    public TbStdTermBscDto getData(TbStdTermBscDto inputDto)  {
        return termMapper.getData(inputDto);
    }

    /**
     * method   : manageData
     * desc     : 용어사전 관리
     */
    public void manageData(TbStdTermBscDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        log.debug(BizUtils.logVoKey(inputDto));
        TbStdTermBscDto outputDto = termMapper.getLockData(inputDto);

        switch (inputDto.getFunc()) {
            case BizConstants.FUNC_SE.INS:
                if (outputDto != null) {
                    throw new BizException(ResponseCode.DUPLICATE_DATA);
                }
                if (termMapper.insertData(inputDto) == 0) {
                    throw new BizException(ResponseCode.INSERT_FAILED);
                }
                break;
            case BizConstants.FUNC_SE.UPD:
                if (outputDto == null) {
                    throw new BizException(ResponseCode.DATA_NOT_FOUND);
                }
                if (termMapper.updateData(inputDto) == 0) {
                    throw new BizException(ResponseCode.UPDATE_FAILED);
                }
                break;
            case BizConstants.FUNC_SE.DEL:
                if (outputDto == null) {
                    throw new BizException(ResponseCode.DATA_NOT_FOUND);
                }
                if (termMapper.deleteData(inputDto) == 0) {
                    throw new BizException(ResponseCode.DELETE_FAILED);
                }
                break;
            default:
                throw new BizException(ResponseCode.VALIDATION_FAILED);
        }
        log.debug(BizUtils.logInfo("END"));
    }

    /**
     * method   : getTermSplitData
     * desc     : 용어사전 단어목록 조회
     */
    public TbStdTermBscDto getTermSplitData(TbStdTermBscDto inputDto)  {

        // 1. 용어사전
        TbStdTermBscDto tbStdTermBscDto = termMapper.getData(inputDto);
        if (tbStdTermBscDto != null) {
            tbStdTermBscDto.setSnake(BizUtils.snakeToLower(tbStdTermBscDto.getEngNm()));
            tbStdTermBscDto.setCamel(BizUtils.snakeToCamel(tbStdTermBscDto.getEngNm()));
            tbStdTermBscDto.setPascal(BizUtils.snakeToPascal(tbStdTermBscDto.getEngNm()));
            tbStdTermBscDto.setStat("표준용어");
            return tbStdTermBscDto;
        }

        TbStdTermBscDto outputDto = new TbStdTermBscDto();
        String inText = inputDto.getTrmNm().replace(" ", "");
        log.debug(BizUtils.logInfo("inText", inText));

        // 2. 단어 테이블에 있는 단어로 자동 분리
        List<WordMappingDto> keywords = extractKeywords(inText);

        // 3. DB 조회 및 Snake Case 변환 실행
        String outTxt = translateToSnakeCase(keywords);

        outputDto.setTrmNm(inText);
        outputDto.setEngNm(outTxt);

        outputDto.setSnake(BizUtils.snakeToLower(outTxt));
        outputDto.setCamel(BizUtils.snakeToCamel(outTxt));
        outputDto.setPascal(BizUtils.snakeToPascal(outTxt));

        outputDto.setDmnNm("");
        outputDto.setTrmExpln(prettyPrintKeywords(keywords));
        outputDto.setStat("0");

        if (BizConstants.KOREAN_PATTERN.matcher(outTxt).find()) {
            outputDto.setStat("단어미등록");
        }

        log.debug(BizUtils.logInfo("outTxt", outTxt + "|" + outputDto.getTrmExpln()));
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
        TbStdWordBscDto wordDto = new TbStdWordBscDto();
        wordDto.setWordNm(wordNm);
        wordDto = wordMapper.getDataByName(wordDto);
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


    public List<TbStdTermBscDto> uploadTermExcelOnly(MultipartFile file) throws Exception {
        List<TbStdTermBscDto> result = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            TbStdTermBscDto dto = new TbStdTermBscDto();

            dto.setTrmNm(BizUtils.getCell(row, 1));
            dto.setEngNm("");
            dto.setSnake("");
            dto.setPascal("");
            dto.setCamel("");
            dto.setStat("");

            result.add(dto);
        }
        return result;
    }

    public List<TbStdTermBscDto> parseExcelPreview(MultipartFile file) throws Exception {
        List<TbStdTermBscDto> result = new ArrayList<>();

        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            TbStdTermBscDto dto = new TbStdTermBscDto();

            dto.setTrmNm(BizUtils.getCell(row, 1));
            dto.setEngNm(BizUtils.getCell(row, 2));
            dto.setDmnNm(BizUtils.getCell(row, 3));
            dto.setTrmExpln(BizUtils.getCell(row, 4));
            dto.setStat(BizUtils.getCell(row, 5));
            dto.setCrtId(BizUtils.getCell(row, 6));

            // 검증 로직
            String error = validateRow(dto);
            if (StringUtil.notNullNorEmpty(error))  dto.setStat(error);

            result.add(dto);
        }
        return result;
    }

    public List<TbStdTermBscDto> parseTermReload(List<TbStdTermBscDto> inputList) {
        List<TbStdTermBscDto> result = new ArrayList<>();
        for (TbStdTermBscDto dto : inputList) {

            if (StringUtil.notNullNorEmpty(dto.getTrmNm())) {
                TbStdTermBscDto outDto = getTermSplitData(dto);
                if (StringUtil.isNullOrEmpty(outDto.getDmnNm())) {
                    outDto.setDmnNm("-");
                }
                result.add(outDto);
            }
        }
        return result;
    }


    private String validateRow(TbStdTermBscDto dto) {
        if (dto.getTrmNm() == null || dto.getTrmNm().isEmpty()) return "용어명 누락";
        if (dto.getEngNm() == null || dto.getEngNm().isEmpty()) return "영문명 누락";
        // DB 중복 체크
        int exists = termMapper.countCode(dto);
        if (exists > 0) return "이미 존재하는 코드";
        return null;  // 정상
    }

    public int uploadTermExcelSave(List<TbStdTermBscDto> list)  {
        int count = 0;
        for (TbStdTermBscDto dto : list) {
            if (StringUtils.equals(dto.getStat(), "0")) {
                int exists = termMapper.countCode(dto);
                if (exists == 0) {
                    dto.setUpdId(dto.getCrtId());
                    termMapper.insertData(dto);
                    count++;
                }
            }
        }
        return count;
    } 
}

