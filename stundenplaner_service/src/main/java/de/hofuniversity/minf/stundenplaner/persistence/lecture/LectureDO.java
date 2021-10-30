package de.hofuniversity.minf.stundenplaner.persistence.lecture;

import de.hofuniversity.minf.stundenplaner.common.NotFoundException;
import de.hofuniversity.minf.stundenplaner.persistence.program.data.ProgramDO;
import de.hofuniversity.minf.stundenplaner.service.to.LectureTO;
import de.hofuniversity.minf.stundenplaner.service.to.LessonTO;
import de.hofuniversity.minf.stundenplaner.service.to.ProgramTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lectures")
public class LectureDO {

    @Id
    @GeneratedValue(generator = "lecture_seq")
    @SequenceGenerator(name = "lecture_seq", sequenceName = "lecture_seq", allocationSize = 1)
    private Long id;
    @Column(name = "name")
    private String lectureName;
    @Column
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lecture")
    private List<LessonDO> lessons;
    public static LectureDO fromTO(LectureTO lectureTo) {
        return new LectureDO(
                lectureTo.getId(),
                lectureTo.getLectureName(),
                lectureTo.getLessons().stream()
                        .map(LessonDO::fromTO)
                        .collect(Collectors.toList())
        );
    }
    public void update(LectureTO lectureTO) {
        setLectureName(lectureTO.getLectureName());
        setLessons(lectureTO.getLessons().stream()
                .map(lessonTO -> LessonDO.fromTO(lessonTO, this))
                .collect(Collectors.toList()));
    }

    public void removeLesson(long idLesson) {
        List<LessonDO> lessons = getLessons().stream().filter(lessonDO -> lessonDO.getId() == idLesson).collect(Collectors.toList());
        if (lessons.size()<1)
            throw new NotFoundException(LessonDO.class, idLesson);
        getLessons().remove(lessons.get(0));
    }
}

