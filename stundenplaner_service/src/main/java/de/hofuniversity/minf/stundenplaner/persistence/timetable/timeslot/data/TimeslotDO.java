package de.hofuniversity.minf.stundenplaner.persistence.timetable.timeslot.data;

import de.hofuniversity.minf.stundenplaner.service.to.TimeslotTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_timeslot")
public class TimeslotDO {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "start")
    private LocalTime start;

    @Column(name = "end")
    private LocalTime end;

    @Column(name = "weekday_nr")
    private Integer weekdayNr;

    public static TimeslotDO fromTO(TimeslotTO timeslotTO){
        return new TimeslotDO(
                timeslotTO.getId(),
                timeslotTO.getStart(),
                timeslotTO.getEnd(),
                timeslotTO.getWeekdayNr()
        );
    }

    public void updateFromTO(TimeslotTO timeslotTO) {
        this.setStart(timeslotTO.getStart());
        this.setEnd(timeslotTO.getEnd());
        this.setWeekdayNr(timeslotTO.getWeekdayNr());
    }
}
