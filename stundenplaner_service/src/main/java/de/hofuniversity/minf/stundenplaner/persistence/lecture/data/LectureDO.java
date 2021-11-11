package de.hofuniversity.minf.stundenplaner.persistence.lecture.data;

import de.hofuniversity.minf.stundenplaner.persistence.timetable.data.LessonDO;
import de.hofuniversity.minf.stundenplaner.service.to.LectureTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_lecture")
public class LectureDO {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(generator = "t_lecture_seq")
    @SequenceGenerator(name = "t_lecture_seq", sequenceName = "t_lecture_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "lectureDO")
    private List<LessonDO> lessons;

    public static LectureDO fromTO(LectureTO lectureTO){
        return new LectureDO(
                lectureTO.getId(),
                lectureTO.getLectureName(),
                Collections.emptyList()
        );
    }

    public void updateFromTO(LectureTO lectureTo) {
        this.setName(lectureTo.getLectureName());
    }
}
