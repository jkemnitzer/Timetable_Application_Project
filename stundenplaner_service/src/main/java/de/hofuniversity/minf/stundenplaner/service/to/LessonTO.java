package de.hofuniversity.minf.stundenplaner.service.to;

import de.hofuniversity.minf.stundenplaner.persistence.timetable.data.LessonDO;
import de.hofuniversity.minf.stundenplaner.persistence.user.data.UserDO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LessonTO {

    private Long id;
    private String lessonType;
    private String note;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer weekdayNr;
    private Long timeslotId;
    private String room;
    private Long roomId;
    private String lecturer;
    private Long lecturerId;
    private String lectureTitle;
    private Long lectureId;
    private Long versionId;

    public static LessonTO fromDO(LessonDO lessonDO) {
        return new LessonTO(
                lessonDO.getId(),
                lessonDO.getLessonType().name(),
                lessonDO.getNote(),
                lessonDO.getTimeslotDO().getStart(),
                lessonDO.getTimeslotDO().getEnd(),
                lessonDO.getTimeslotDO().getWeekdayNr(),
                lessonDO.getTimeslotDO().getId(),
                lessonDO.getRoomDO().getRoomNumber(),
                lessonDO.getRoomDO().getId(),
                buildLecturerName(lessonDO.getLecturerDO()),
                lessonDO.getLecturerDO().getId(),
                lessonDO.getLectureDO().getName(),
                lessonDO.getLectureDO().getId(),
                lessonDO.getTimeTableVersionDO().getId()
        );
    }

    private static String buildLecturerName(UserDO userDO) {
        StringBuilder builder = new StringBuilder();
        if (validateString(userDO.getTitle())) {
            builder.append(userDO.getTitle()).append(" ");
        }
        if (validateString(userDO.getFirstName())){
            builder.append(userDO.getFirstName()).append(" ");
        }
        if (validateString(userDO.getLastName())){
            builder.append(userDO.getLastName());
        }
        return builder.toString().trim();
    }

    private static boolean validateString(String s){
        return s != null && !s.isEmpty() && !s.isBlank();
    }

}
