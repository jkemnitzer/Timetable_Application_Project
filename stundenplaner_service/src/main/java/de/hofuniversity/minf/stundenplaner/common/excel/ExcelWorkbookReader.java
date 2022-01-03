package de.hofuniversity.minf.stundenplaner.common.excel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;
import java.util.List;

public abstract class ExcelWorkbookReader<T extends ExcelImportable> {

    protected Workbook workbook;

    public ExcelWorkbookReader(Workbook workbook) {
        this.workbook = workbook;
    }

    public abstract ExcelWorkbookReader<T> extractWorkbookSheet(String sheetName);

    protected List<String> extractHeaders(Sheet sheet) {
        List<String> headers = new ArrayList<>(sheet.getRow(0).getPhysicalNumberOfCells());
        sheet.getRow(0).forEach(cell -> headers.add(cell.getStringCellValue()));
        return headers;
    }

    protected List<Row> extractDataRows(Sheet sheet) {
        List<Row> rows = new ArrayList<>(sheet.getPhysicalNumberOfRows());
        sheet.forEach(row -> {
            if (row.getRowNum() != 0) {
                rows.add(row);
            }
        });
        return rows;
    }

}
