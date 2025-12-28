package com.meta.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/meta")
public class TemplateController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());


    /**
     * @ ID : downloadTermExcelTemplate
     * @ NAME : 표쥰용어 UPLOAD Template
     */
    @GetMapping("/downloadTermExcelTemplate")
    public void downloadTermExcelTemplate(HttpServletResponse response) throws Exception {
        downloadExcelTemplate(
                response,
                "표준용어",
                "표준용어_업로드_템플릿.xlsx",
                new String[]{"번호","용어명","영문명","도메인명","설명","상태"},
                new String[]{
                        "1",
                        "감면등록일자",
                        "RDCT_APLY_YMD",
                        "연월일C8",
                        "매겨야 할 부담 따위를 덜어 주거나 면제해 줄 것을 알려 요청한 날짜",
                        "0"
                }
        );
    }

    /**
     * @ ID : downloadWordExcelTemplate
     * @ NAME : 표준단어 UPLOAD Template
     */
    @GetMapping("/downloadWordExcelTemplate")
    public void downloadWordExcelTemplate(HttpServletResponse response) throws Exception {
        downloadExcelTemplate(
                response,
                "표준단어",
                "표준단어_업로드_템플릿.xlsx",
                new String[] { "번호","단어명", "영문약어명 ", "영문명", "설명", "상태"},
                new String[] {
                        "1",
                        "객체",
                        "OBJT",
                        "Object",
                        "客體. 의사나 행위가 미치는 대상 또는 모두 포함한 개념",
                        "0"
                }
        );
    }
    /**
     * @ ID : downloadDmnExcelTemplate
     * @ NAME : 도메인 UPLOAD Template
     */
    @GetMapping("/downloadDmnExcelTemplate")
    public void downloadDmnExcelTemplate(HttpServletResponse response) throws Exception {
        downloadExcelTemplate(
                response,
                "표준도메인",
                "표준도메인_업로드_템플릿.xlsx",
                new String[] { "번호","도메인명", "도메인분류명", "도메인영문명", "도메인속성", "상태"},
                new String[] {
                        "1",
                        "가격N10",
                        "가격",
                        "PRC",
                        "N10",
                        "0"
                }
        );
    }

    /**
     * @ ID : downloadCodeGroupExcelTemplate
     * @ NAME : 그룹코드 UPLOAD Template
     */
    @GetMapping("/downloadCodeGroupExcelTemplate")
    public void downloadCodeGroupExcelTemplate(HttpServletResponse response) throws Exception {
        downloadExcelTemplate(
            response,
            "그룹코드",
            "그룹코드_업로드_템플릿.xlsx",
            new String[] { "번호", "공통코드", "공통코드명",  "비고", "상태" },
            new String[] {
                    "1",
                    "SYS",
                    "시스템",
                    "-",
                    "1"
            }
        );
    }


    /**
     * @ ID : downloadCodeExcelTemplate
     * @ NAME : 상세코드 UPLOAD Template
     */
    @GetMapping("/downloadCodeExcelTemplate")
    public void downloadCodeExcelTemplate(HttpServletResponse response) throws Exception {
        downloadExcelTemplate(
                response,
                "상세코드",
                "상세코드_업로드_템플릿.xlsx",
                new String[] { "번호", "공통코드", "공통코드명", "상세코드", "상세코드명", "정렬순서", "비고", "상태" },
                new String[] {
                        "1",
                        "SYS",
                        "시스템",
                        "ROLE",
                        "권한코드",
                        "1",
                        ".",
                        "1"
                }
        );
    }


    /**
     * @ ID : downloadUserExcelTemplate
     * @ NAME : 사용자정보 UPLOAD Template
     */
    @GetMapping("/downloadUserExcelTemplate")
    public void downloadUserExcelTemplate(HttpServletResponse response) throws Exception {
        downloadExcelTemplate(
                response,
                "사용자정보",
                "사용자정보_업로드_템플릿.xlsx",
                new String[] { "번호", "사용자ID", "사용자명",  "이메일", "전화번호", "권한", "상태" },
                new String[] {
                        "1",
                        "사용자ID",
                        "사용자명",
                        "이메일",
                        "전화번호",
                        "권한",
                        "1"
                }
        );
    }



    private void downloadExcelTemplate(
            HttpServletResponse response,
            String sheetName,
            String fileName,
            String[] headers,
            String[] sampleData
    ) throws Exception {

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet(sheetName);

            // Header
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            // Sample
            Row sampleRow = sheet.createRow(1);
            for (int i = 0; i < sampleData.length; i++) {
                sampleRow.createCell(i).setCellValue(sampleData[i]);
            }

            // Column Size
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Response
            response.setContentType(
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            );
            response.setHeader(
                    "Content-Disposition",
                    "attachment; filename=" +
                            java.net.URLEncoder.encode(fileName, "UTF-8").replace("+", "%20")
            );

            workbook.write(response.getOutputStream());
        }
    }
}

