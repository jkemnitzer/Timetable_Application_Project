package de.hofuniversity.minf.stundenplaner.service.to;

import de.hofuniversity.minf.stundenplaner.common.excel.ExcelImportable;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.version.TimeTableVersionDO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimeTableVersionTO implements ExcelImportable {

    private Long id;
    private String semesterYear;
    private String version;
    private String comment;

    public static TimeTableVersionTO fromDO(TimeTableVersionDO versionDO) {
        return new TimeTableVersionTO(
                versionDO.getId(),
                versionDO.getSemesterYear(),
                versionDO.getVersion(),
                versionDO.getComment()
        );
    }

    @Override
    public void readValueMap(Map<String, String> map) {
        this.setId(Long.parseLong(map.get("Version ID:")));
        this.setVersion(map.get("Version:"));
        this.setComment(map.get("Kommentar:"));
        this.setSemesterYear(map.get("Semester:"));
    }
}
