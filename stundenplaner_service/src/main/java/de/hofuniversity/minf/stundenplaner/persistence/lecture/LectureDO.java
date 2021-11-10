package de.hofuniversity.minf.stundenplaner.persistence.lecture;

import de.hofuniversity.minf.stundenplaner.service.to.LectureTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_lectures")
public class LectureDO {

    @Id
    @GeneratedValue(generator = "t_lecture_seq")
    @SequenceGenerator(name = "t_lecture_seq", sequenceName = "t_lecture_seq", allocationSize = 1)
    private Long id;
    @Column(name = "lecture_name")
    private String lectureName;
    @Column
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "lecture",cascade = CascadeType.ALL)
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

    }


}

