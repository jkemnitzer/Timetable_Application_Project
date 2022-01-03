package de.hofuniversity.minf.stundenplaner.common.excel;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Slf4j
public class ExcelWorkbookBuilder {

    protected final Workbook workbook;
    protected Sheet itemSheet;

    public ExcelWorkbookBuilder() {
        this.workbook = new HSSFWorkbook();
    }

    public ExcelWorkbookBuilder addItemsToSheet(String sheetKey, List<ExcelExportable> items) {
        this.itemSheet = this.getSheet(sheetKey);
        List<String> headers = createHeaders(items.stream().findAny().orElseThrow(IllegalArgumentException::new));
        items.forEach(item -> addItemRow(this.itemSheet, item));
        IntStream.range(0, headers.size()).forEach(itemSheet::autoSizeColumn);
        return this;
    }

    public Workbook build() {
        return this.workbook;
    }

    protected List<String> createHeaders(ExcelExportable template) {
        List<String> headers = new ArrayList<>(template.getValueMap().keySet());
        Row row = itemSheet.createRow(0);
        headers.forEach(field -> {
            Cell c = row.createCell(row.getPhysicalNumberOfCells());
            c.setCellValue(field);
            this.setCellStyle(c);
        });
        return headers;
    }

    protected void addSingleValueRow(Sheet sheet, String key, String value) {
        Row row = sheet.createRow(sheet.getPhysicalNumberOfRows());
        Cell keyCell = row.createCell(row.getPhysicalNumberOfCells());
        keyCell.setCellValue(key + ":");
        Cell valueCell = row.createCell(row.getPhysicalNumberOfCells());
        valueCell.setCellValue(value);
    }

    protected void addItemRow(Sheet sheet, ExcelExportable item) {
        Row row = sheet.createRow(sheet.getPhysicalNumberOfRows());
        Map<String, String> map = item.getValueMap();
        map.keySet().forEach(key -> {
            Cell cell = row.createCell(row.getPhysicalNumberOfCells());
            cell.setCellValue(map.get(key));
        });
    }

    protected Sheet getSheet(String key) {
        Sheet sheet = workbook.getSheet(key);
        return sheet == null ? workbook.createSheet(key) : sheet;
    }

    protected void setCellStyle(Cell cell) {
        final CellStyle style = cell.getSheet().getWorkbook().createCellStyle();
        final BorderStyle borderStyle = BorderStyle.MEDIUM;
        final Font font = cell.getSheet().getWorkbook().createFont();
        style.setBorderTop(borderStyle);
        style.setBorderRight(borderStyle);
        style.setBorderBottom(borderStyle);
        style.setBorderLeft(borderStyle);
        font.setBold(true);
        style.setFont(font);
        cell.setCellStyle(style);
    }

}
