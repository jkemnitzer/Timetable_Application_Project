package de.hofuniversity.minf.stundenplaner.service.boundary;

import de.hofuniversity.minf.stundenplaner.service.to.LessonTO;
import de.hofuniversity.minf.stundenplaner.service.to.TimeTableTO;
import org.apache.poi.ss.usermodel.Workbook;

import java.time.LocalTime;
import java.util.List;

public interface TimeTableService {

    List<LessonTO> findAllLessons(
            Long programId, Long semesterId, Integer weekdayNr, Long versionId, Long lecturerId, LocalTime start, LocalTime end);

    TimeTableTO findAllLessonsByVersion(Long programId, Long semesterId, Integer weekdayNr, Long id, Long versionId, LocalTime start, LocalTime end);

    TimeTableTO createTimeTable(TimeTableTO timeTableTO);

    TimeTableTO deleteTimeTable(Long versionId);

    LessonTO createSingleLesson(LessonTO lessonTO);

    LessonTO findSingleLesson(Long id);

    LessonTO deleteLesson(Long id);

    LessonTO updateLesson(Long id, LessonTO lessonTO);

    Workbook exportAll();

    Workbook exportVersion(Long versionId);


}
