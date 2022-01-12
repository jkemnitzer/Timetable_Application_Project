package de.hofuniversity.minf.stundenplaner.service.to;

import de.hofuniversity.minf.stundenplaner.persistence.lecture.data.LectureDO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LectureTO {

    private Long id;
    private String lectureName;
    private List<LessonTO> lessons;
    private FacultyTO primaryFaculty;

    public static LectureTO fromDO(LectureDO lectureDO) {
        return new LectureTO(
                lectureDO.getId(),
                lectureDO.getName(),
                lectureDO.getLessons().stream()
                        .map(LessonTO::fromDO)
                        .toList(),
                FacultyTO.fromDO(lectureDO.getPrimaryFaculty())
        );
    }

}