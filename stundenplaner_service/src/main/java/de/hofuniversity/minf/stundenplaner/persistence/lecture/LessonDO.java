package de.hofuniversity.minf.stundenplaner.persistence.lecture;


import de.hofuniversity.minf.stundenplaner.service.to.LessonTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lessons")
public class LessonDO {

    @Id
    @GeneratedValue(generator = "lesson_seq")
    @SequenceGenerator(name = "lesson_seq", sequenceName = "lesson_seq", allocationSize = 1)
    private Long id;
    @Column(name = "name")
    private String lessonName;
    @Column
    private String prof;
    @Column
    private String roomRequirement;
    @ManyToOne(fetch = FetchType.LAZY)
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

