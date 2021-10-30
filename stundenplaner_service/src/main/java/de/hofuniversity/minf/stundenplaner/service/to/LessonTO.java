package de.hofuniversity.minf.stundenplaner.service.to;

import de.hofuniversity.minf.stundenplaner.persistence.lecture.LessonDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonTO {

    private Long id;
    private String lessonName;
    private String prof;
    private String roomRequirement;

    public static LessonTO fromDO(LessonDO lessonDO) {
        return new LessonTO(
                lessonDO.getId(),
                lessonDO.getLessonName(),
                lessonDO.getProf(),
                lessonDO.getRoomRequirement()
                );

    }

}
