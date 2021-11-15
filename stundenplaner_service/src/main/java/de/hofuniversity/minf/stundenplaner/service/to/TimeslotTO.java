package de.hofuniversity.minf.stundenplaner.service.to;

import de.hofuniversity.minf.stundenplaner.persistence.timetable.timeslot.data.TimeslotDO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeslotTO {

    private Long id;
    private LocalTime start;
    private LocalTime end;
    private Integer weekdayNr;

    public static TimeslotTO fromDO(TimeslotDO timeslotDO){
        return new TimeslotTO(
                timeslotDO.getId(),
                timeslotDO.getStart(),
                timeslotDO.getEnd(),
                timeslotDO.getWeekdayNr()
        );
    }


}
