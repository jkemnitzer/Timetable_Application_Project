package de.hofuniversity.minf.stundenplaner.persistence.timetable.version;

import de.hofuniversity.minf.stundenplaner.service.to.TimeTableVersionTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_timetable_version")
public class TimeTableVersionDO {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(generator = "t_timetable_version_seq")
    @SequenceGenerator(name = "t_timetable_version_seq", sequenceName = "t_timetable_version_seq", allocationSize = 1)
    private Long id;

    @Column(name = "semester_year")
    private String semesterYear;

    @Column(name = "version")
    private String version;

    @Column(name = "comment")
    private String comment;

    public static TimeTableVersionDO fromTO(TimeTableVersionTO to){
        return new TimeTableVersionDO(
            to.getId(),
            to.getSemesterYear(),
            to.getVersion(),
            to.getComment()
        );
    }
}
