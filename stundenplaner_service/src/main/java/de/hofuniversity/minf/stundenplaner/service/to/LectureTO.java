package de.hofuniversity.minf.stundenplaner.service.to;

import de.hofuniversity.minf.stundenplaner.persistence.lecture.LectureDO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LectureTO {

    private Long id;
    private String lectureName;
    private List<LessonTO> lessons;

    public static LectureTO fromDO(LectureDO lectureDO) {
        return new LectureTO(
                lectureDO.getId(),
                lectureDO.getLectureName(),
                lectureDO.getLessons().stream()
                        .map(LessonTO::fromDO)
                        .collect(Collectors.toList())
        );
    }

}
