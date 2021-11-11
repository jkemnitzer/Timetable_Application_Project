package de.hofuniversity.minf.stundenplaner.persistence.timetable.data;

import de.hofuniversity.minf.stundenplaner.persistence.lecture.data.LectureDO;
import de.hofuniversity.minf.stundenplaner.persistence.room.data.RoomDO;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.timeslot.data.TimeslotDO;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.version.TimeTableVersionDO;
import de.hofuniversity.minf.stundenplaner.persistence.user.data.UserDO;
import de.hofuniversity.minf.stundenplaner.service.to.LessonTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_timetable")
@Entity
public class LessonDO {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "t_timetable_seq")
    @SequenceGenerator(name = "t_timetable_seq", sequenceName = "t_timetable_seq", allocationSize = 1)
    private Long id;

    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    private LessonType lessonType;

    @Column(name = "note")
    private String note;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "fk_room", nullable = false)
    private RoomDO roomDO;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "fk_lecturer_user", nullable = false)
    private UserDO lecturerDO;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "fk_lecture", nullable = false)
    private LectureDO lectureDO;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "fk_timeslot", nullable = false)
    private TimeslotDO timeslotDO;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "fk_version", nullable = false)
    private TimeTableVersionDO timeTableVersionDO;

    public static LessonDO fromTO(LessonTO lessonTO) {
        return new LessonDO(
                lessonTO.getId(),
                LessonType.valueOf(lessonTO.getLessonType()),
                lessonTO.getNote(),
                null, null, null, null, null
        );
    }

    public void updateFromTO(LessonTO lessonTO) {
        this.setNote(lessonTO.getNote());
        this.setLessonType(LessonType.valueOf(lessonTO.getLessonType()));
    }
}
