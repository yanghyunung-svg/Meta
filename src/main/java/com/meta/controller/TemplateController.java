package com.meta.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
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
     * @ ID : downloadCodeExcelTemplate
     * @ NAME : 상세코드 UPLOAD Template
     */
    @GetMapping("/downloadCodeExcelTemplate")
    public void downloadCodeExcelTemplate(HttpServletResponse response) throws Exception {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("상세코드");

        // Header 생성
        Row header = sheet.createRow(0);
        String[] titles = { "번호", "공통코드", "공통코드명", "상세코드", "상세코드명", "정렬순서", "비고", "상태", "신청자" };

        for (int i = 0; i < titles.length; i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(titles[i]);
            sheet.autoSizeColumn(i);
        }

        // 샘플 데이터 (옵션)
        Row sample = sheet.createRow(1);
        sample.createCell(0).setCellValue("1");
        sample.createCell(1).setCellValue("SYS");
        sample.createCell(2).setCellValue("시스템");
        sample.createCell(3).setCellValue("ROLE");
        sample.createCell(4).setCellValue("권한코드");
        sample.createCell(5).setCellValue("1");
        sample.createCell(6).setCellValue("....");
        sample.createCell(7).setCellValue("1");
        sample.createCell(8).setCellValue("");

        // 파일명
        String fileName = "상세코드_업로드_템플릿.xlsx";

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" +
                java.net.URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20"));

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    /**
     * @ ID : downloadTermExcelTemplate
     * @ NAME : 표쥰용어 UPLOAD Template
     */
    @GetMapping("/downloadTermExcelTemplate")
    public void downloadTermExcelTemplate(HttpServletResponse response) throws Exception {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("용어");

        // Header 생성
        Row header = sheet.createRow(0);
        String[] titles = { "번호","용어명", "영문명", "도메인명", "설명", "상태","신청자" };

        for (int i = 0; i < titles.length; i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(titles[i]);
            sheet.autoSizeColumn(i);
        }

        // 샘플 데이터 (옵션)
        Row sample = sheet.createRow(1);
        sample.createCell(0).setCellValue("1");
        sample.createCell(1).setCellValue("감면신청일자");
        sample.createCell(2).setCellValue("RDCT_APLY_YMD");
        sample.createCell(3).setCellValue("연월일C8");
        sample.createCell(4).setCellValue("매겨야 할 부담 따위를 덜어 주거나 면제해 줄 것을 알려 요청한 날짜");
        sample.createCell(5).setCellValue("0");
        sample.createCell(6).setCellValue("");

        // 파일명
        String fileName = "표준용어_업로드_템플릿.xlsx";

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" +
                java.net.URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20"));

        workbook.write(response.getOutputStream());
        workbook.close();
    }

}
