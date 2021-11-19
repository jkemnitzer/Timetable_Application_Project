package de.hofuniversity.minf.stundenplaner.common.excel;

import de.hofuniversity.minf.stundenplaner.service.to.LessonTO;
import de.hofuniversity.minf.stundenplaner.service.to.TimeTableVersionTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;

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
        return this;
    }

    public TimeTableWorkBookBuilder addVersionInfo(TimeTableVersionTO version) {
        Sheet versionSheet = workbook.createSheet("Informationen");
        this.addSingleValueRow(versionSheet, "Version ID", String.valueOf(version.getId()));
        this.addSingleValueRow(versionSheet, "Version", version.getVersion());
        this.addSingleValueRow(versionSheet, "Kommentar", version.getComment());
        return this;
    }

}
