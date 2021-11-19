package de.hofuniversity.minf.stundenplaner.common.excel;

import de.hofuniversity.minf.stundenplaner.service.to.LessonTO;
import de.hofuniversity.minf.stundenplaner.service.to.TimeTableVersionTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
public class TimeTableWorkBookBuilder extends GenericWorkbookBuilder<LessonTO> {

    public TimeTableWorkBookBuilder() {
        super();
    }

    public TimeTableWorkBookBuilder addItemsToSheet(List<LessonTO> items){
        return this.addItemsToSheet("Stundenplan", items);
    }

    @Override
    public TimeTableWorkBookBuilder addItemsToSheet(String sheetKey, List<LessonTO> items) {
        final Sheet sheet = super.getSheet(sheetKey);
        final List<String> titles = List.of("Wochentag", "Start", "Ende", "Raum", "Titel", "Dozent", "Typ", "Notiz");
        this.addHeaders(sheet, titles);
        this.sortLessons(items).forEach(item -> {
            Row row = sheet.createRow(sheet.getPhysicalNumberOfRows());
            row.createCell(row.getPhysicalNumberOfCells()).setCellValue(DayOfWeek.of(item.getWeekdayNr()).toString());
            row.createCell(row.getPhysicalNumberOfCells()).setCellValue(item.getStartTime().toString());
            row.createCell(row.getPhysicalNumberOfCells()).setCellValue(item.getEndTime().toString());
            row.createCell(row.getPhysicalNumberOfCells()).setCellValue(item.getRoom());
            row.createCell(row.getPhysicalNumberOfCells()).setCellValue(item.getLectureTitle());
            row.createCell(row.getPhysicalNumberOfCells()).setCellValue(item.getLecturer());
            row.createCell(row.getPhysicalNumberOfCells()).setCellValue(item.getLessonType());
            row.createCell(row.getPhysicalNumberOfCells()).setCellValue(item.getNote());
        });
        IntStream.range(0, titles.size()).forEach(sheet::autoSizeColumn);
        return this;
    }

    public TimeTableWorkBookBuilder addVersionInfo(TimeTableVersionTO version) {
        Sheet versionSheet = workbook.createSheet("Informationen");
        this.addSingleValueRow(versionSheet, "Version ID", String.valueOf(version.getId()));
        this.addSingleValueRow(versionSheet, "Version", version.getVersion());
        this.addSingleValueRow(versionSheet, "Kommentar", version.getComment());
        return this;
    }

    private void addHeaders(Sheet sheet, List<String> headers) {
        final Row row = sheet.createRow(sheet.getPhysicalNumberOfRows());
        headers.forEach(title -> {
            Cell cell = row.createCell(row.getPhysicalNumberOfCells());
            cell.setCellValue(title);
            this.setCellStyle(cell);
        });
    }

    private List<LessonTO> sortLessons(List<LessonTO> list) {
        List<LessonTO> newList = new ArrayList<>(list);
        newList.sort(Comparator.comparing(LessonTO::getWeekdayNr)
                .thenComparing(LessonTO::getStartTime)
                .thenComparing(LessonTO::getLectureTitle));
        return newList;
    }

    private void setCellStyle(Cell cell) {
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
