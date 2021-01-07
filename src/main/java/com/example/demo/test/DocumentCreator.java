package com.example.demo.test;

import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class DocumentCreator {

    private static final Logger logger = LoggerFactory.getLogger(DocumentCreator.class);
    private static final String EXCEL_NAME = "Result.xlsx";

    @SneakyThrows
    public void createDocument(int numberSheet) {
        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet("sheet" + numberSheet);
        Row rowWithNames = sheet.createRow(0);
        Cell team = rowWithNames.createCell(0);
        team.setCellValue("Команда");

        Cell e = rowWithNames.createCell(1);
        e.setCellValue("И");

        Cell b = rowWithNames.createCell(2);
        b.setCellValue("В");

        Cell bo = rowWithNames.createCell(3);
        bo.setCellValue("ВО");

        Cell po = rowWithNames.createCell(4);
        po.setCellValue("ПО");

        Cell p = rowWithNames.createCell(5);
        p.setCellValue("П");

        Cell vo = rowWithNames.createCell(6);
        vo.setCellValue("ВО");

        Cell form = rowWithNames.createCell(7);
        form.setCellValue("Форма");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 7, 13));
        sheet.autoSizeColumn(1);
        try {
            book.write(new FileOutputStream(EXCEL_NAME));
        } catch (IOException ioException) {
            logger.error("Exception while writing to the file", ioException);
        }
        book.close();
    }

    @SneakyThrows
    public void addNewRowToFile(String v1, String v2, String v3, String v4,
                                String v5, String v6, String v7, String v8,
                                String v9, String v10, String v11, String v12,  String v13,
                                int numberRow, int numberSheet) {
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(EXCEL_NAME));
        HSSFSheet sheet = workbook.getSheet("sheet" + numberSheet);
        Row rowWithNames = sheet.createRow(numberRow);

        Cell c1 = rowWithNames.createCell(0);
        c1.setCellValue(v1);
        Cell c2 = rowWithNames.createCell(1);
        c1.setCellValue(v2);
        Cell c3 = rowWithNames.createCell(2);
        c1.setCellValue(v3);
        Cell c4 = rowWithNames.createCell(3);
        c1.setCellValue(v4);
        Cell c5 = rowWithNames.createCell(4);
        c1.setCellValue(v5);
        Cell c6 = rowWithNames.createCell(5);
        c1.setCellValue(v6);
        Cell c7 = rowWithNames.createCell(6);
        c1.setCellValue(v7);
        Cell c8 = rowWithNames.createCell(7);
        c1.setCellValue(v8);
        Cell c9 = rowWithNames.createCell(8);
        c1.setCellValue(v9);
        Cell c10 = rowWithNames.createCell(9);
        c1.setCellValue(v10);
        Cell c11 = rowWithNames.createCell(10);
        c1.setCellValue(v11);
        Cell c12 = rowWithNames.createCell(11);
        c1.setCellValue(v12);
        Cell c13 = rowWithNames.createCell(12);
        c1.setCellValue(v13);

        workbook.close();
    }
}
