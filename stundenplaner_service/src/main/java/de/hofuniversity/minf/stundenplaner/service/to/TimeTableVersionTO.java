package de.hofuniversity.minf.stundenplaner.service.to;

import de.hofuniversity.minf.stundenplaner.persistence.timetable.version.TimeTableVersionDO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimeTableVersionTO {

    private Long id;
    private String semesterYear;
    private String version;
    private String comment;

    public static TimeTableVersionTO fromDO(TimeTableVersionDO versionDO){
        return new TimeTableVersionTO(
                versionDO.getId(),
                versionDO.getSemesterYear(),
                versionDO.getVersion(),
                versionDO.getComment()
        );
    }

}
