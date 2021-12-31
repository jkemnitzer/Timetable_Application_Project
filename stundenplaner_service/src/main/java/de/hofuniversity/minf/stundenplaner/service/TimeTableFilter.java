package de.hofuniversity.minf.stundenplaner.service;

import de.hofuniversity.minf.stundenplaner.persistence.program.data.ProgramDO;
import de.hofuniversity.minf.stundenplaner.persistence.program.data.SemesterDO;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.data.LessonDO;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.version.TimeTableVersionDO;
import de.hofuniversity.minf.stundenplaner.persistence.user.data.UserDO;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TimeTableFilter {

    public List<LessonDO> filter(
            List<LessonDO> lessons, ProgramDO program, SemesterDO semester, Integer weekdayNr, TimeTableVersionDO version,
            UserDO lecturer, LocalTime start, LocalTime end) {
        Stream<LessonDO> stream = lessons.stream();
        if (program != null) {
            stream = stream.filter(l -> l.getLectureDO().getSemesters().stream().anyMatch(s -> s.getProgram().getId().equals(program.getId())));
        }
        if (semester != null) {
            stream = stream.filter(l -> l.getLectureDO().getSemesters().stream().anyMatch(s -> s.getId().equals(semester.getId())));
        }
        if (weekdayNr != null) {
            stream = stream.filter(l -> weekdayNr.equals(l.getTimeslotDO().getWeekdayNr()));
        }
        if (version != null) {
            stream = stream.filter(l -> version.getId().equals(l.getTimeTableVersionDO().getId()));
        }
        if (lecturer != null) {
            stream = stream.filter(l -> lecturer.getId().equals(l.getLecturerDO().getId()));
        }
        if (start != null) {
            stream = stream.filter(l ->
                    start.minusMinutes(1).isBefore(l.getTimeslotDO().getStart())
                            && start.plusMinutes(1).isAfter(l.getTimeslotDO().getStart())
            );
        }
        if (end != null) {
            stream = stream.filter(l ->
                    end.minusMinutes(1).isBefore(l.getTimeslotDO().getEnd())
                            && end.plusMinutes(1).isAfter(l.getTimeslotDO().getEnd())
            );
        }
        return stream.collect(Collectors.toList());
    }
}
