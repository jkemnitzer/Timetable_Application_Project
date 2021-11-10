package de.hofuniversity.minf.stundenplaner.persistence.lecture;


import de.hofuniversity.minf.stundenplaner.service.to.LessonTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_lessons")
public class LessonDO {

    @Id
    @GeneratedValue(generator = "t_lesson_seq")
    @SequenceGenerator(name = "t_lesson_seq", sequenceName = "t_lesson_seq", allocationSize = 1)
    private Long id;
    @Column(name = "lesson_name")
    private String lessonName;
    @Column(name = "prof")
    private String prof;
    @Column(name = "room_requirement")
    private String roomRequirement;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lecture_id")
    private LectureDO lecture;

    public static LessonDO fromTO(LessonTO lessonTO, LectureDO lectureDO) {
        return new LessonDO(
                lessonTO.getId(),
                lessonTO.getLessonName(),
                lessonTO.getProf(),
                lessonTO.getRoomRequirement(),
                lectureDO
        );
    }

    public static LessonDO fromTO(LessonTO lessonTO){
        return fromTO(lessonTO, null);
    }
    public void update(LessonTO lessonTO) {
        this.setLessonName(lessonTO.getLessonName());
        this .setProf(lessonTO.getProf());
        this.setRoomRequirement(lessonTO.getRoomRequirement());
    }
}

