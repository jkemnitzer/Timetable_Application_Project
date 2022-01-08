package de.hofuniversity.minf.stundenplaner.service.to;

import de.hofuniversity.minf.stundenplaner.common.excel.ExcelExportable;
import de.hofuniversity.minf.stundenplaner.common.excel.ExcelImportable;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.data.LessonDO;
import de.hofuniversity.minf.stundenplaner.persistence.user.data.UserDO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LessonTO implements ExcelExportable, ExcelImportable {

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
        if (validateString(userDO.getFirstName())) {
            builder.append(userDO.getFirstName()).append(" ");
        }
        if (validateString(userDO.getLastName())) {
            builder.append(userDO.getLastName());
        }
        return builder.toString().trim();
    }

    private static boolean validateString(String s) {
        return s != null && !s.isEmpty() && !s.isBlank();
    }

    @Override
    public Map<String, String> getValueMap() {
        Map<String, String> map = new HashMap<>();
        map.put("Id", String.valueOf(getId()));
        map.put("WeekDay", String.valueOf(getWeekdayNr()));
        map.put("Start", String.valueOf(getStartTime()));
        map.put("End", String.valueOf(getEndTime()));
        map.put("Title", String.valueOf(getLectureTitle()));
        map.put("Room", String.valueOf(getRoom()));
        map.put("Lecturer", String.valueOf(getLecturer()));
        map.put("Timeslot Id", String.valueOf(getTimeslotId()));
        map.put("Room Id", String.valueOf(getRoomId()));
        map.put("Lecturer Id", String.valueOf(getLecturerId()));
        map.put("Lecture Id", String.valueOf(getLectureId()));
        map.put("Lesson Type", getLessonType());
        return map;
    }

    @Override
    public void readValueMap(Map<String, String> map) {
        this.setId(Long.parseLong(map.get("Id")));
        this.setTimeslotId(Long.parseLong(map.get("Timeslot Id")));
        this.setRoomId(Long.parseLong(map.get("Room Id")));
        this.setLecturerId(Long.parseLong(map.get("Lecturer Id")));
        this.setLectureId(Long.parseLong(map.get("Lecture Id")));
        this.setWeekdayNr(Integer.parseInt(map.get("WeekDay")));
        this.setStartTime(LocalTime.parse(map.get("Start")));
        this.setEndTime(LocalTime.parse(map.get("End")));
        this.setLectureTitle(map.get("Title"));
        this.setRoom(map.get("Room"));
        this.setLecturer(map.get("Lecturer"));
        this.setLessonType(map.get("Lesson Type"));
    }


}
