package de.hofuniversity.minf.stundenplaner.common.excel;

import de.hofuniversity.minf.stundenplaner.service.to.LessonTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class GenericWorkbookBuilder<T> {

    protected final Workbook workbook;
    protected Sheet itemSheet;

    protected GenericWorkbookBuilder() {
        this.workbook = new HSSFWorkbook();
    }

    public GenericWorkbookBuilder<T> addItemsToSheet(String sheetKey, List<T> items) {
        this.itemSheet = this.getSheet(sheetKey);
        List<String> fields = extractFields();
        Row headers = itemSheet.createRow(0);
        fields.forEach(field -> {
            Cell c = headers.createCell(headers.getPhysicalNumberOfCells());
            c.setCellValue(field);
        });
        items.forEach(item -> addItemRow(this.itemSheet, item));
        return this;
    }

    public Workbook build() {
        return this.workbook;
    }

    protected void addSingleValueRow(Sheet sheet, String key, String value) {
        Row row = sheet.createRow(sheet.getPhysicalNumberOfRows());
        Cell keyCell = row.createCell(row.getPhysicalNumberOfCells());
        keyCell.setCellValue(key + ":");
        Cell valueCell = row.createCell(row.getPhysicalNumberOfCells());
        valueCell.setCellValue(value);
    }

    protected void addItemRow(Sheet sheet, T item) {
        Row row = sheet.createRow(sheet.getPhysicalNumberOfRows());
        try {
            for (Method method : extractMethods()) {
                Object o = method.invoke(item);
                Cell cell = row.createCell(row.getPhysicalNumberOfCells());
                cell.setCellValue(String.valueOf(o));
            }
        } catch (InvocationTargetException | IllegalAccessException e) {
            log.debug("Attempted reflection access on LessonTO failed with exception", e);
            sheet.removeRow(row);
        }
    }

    private List<Method> extractMethods(){
        return Arrays.stream(LessonTO.class.getMethods())
                .filter(method -> method.getName().toLowerCase().startsWith("get"))
                .filter(method -> !method.getName().endsWith("Id") && !method.getName().endsWith("Class"))
                .filter(method -> method.getParameterCount() == 0)
                .toList();
    }

    private List<String> extractFields(){
        return extractMethods().stream()
                .map(method -> method.getName().substring(3))
                .toList();
    }

    protected Sheet getSheet(String key) {
        Sheet sheet = workbook.getSheet(key);
        return sheet == null ? workbook.createSheet(key) : sheet;
    }
    
}
