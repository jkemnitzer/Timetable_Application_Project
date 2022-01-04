package de.hofuniversity.minf.stundenplaner.common.excel;

import de.hofuniversity.minf.stundenplaner.service.to.LessonTO;
import de.hofuniversity.minf.stundenplaner.service.to.TimeTableVersionTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TimeTableWorkBookBuilder extends ExcelWorkbookBuilder {

    private final String SHEET_TITLE = "Stundenplan";

    public TimeTableWorkBookBuilder() {
        super();
    }

    public Workbook buildTemplate() {
        this.itemSheet = super.getSheet(SHEET_TITLE);
        this.createHeaders(new LessonTO());
        return super.build();
    }

    public TimeTableWorkBookBuilder addItemsToSheet(List<LessonTO> lessons) {
        return (TimeTableWorkBookBuilder) this.addItemsToSheet(SHEET_TITLE, new ArrayList<>(lessons));
    }

    public TimeTableWorkBookBuilder addVersionInfo(TimeTableVersionTO version) {
        Sheet versionSheet = workbook.createSheet("Informationen");
        this.addSingleValueRow(versionSheet, "Version ID", String.valueOf(version.getId()));
        this.addSingleValueRow(versionSheet, "Version", version.getVersion());
        this.addSingleValueRow(versionSheet, "Kommentar", version.getComment());
        this.addSingleValueRow(versionSheet, "Semester", version.getSemesterYear());
        return this;
    }
}
