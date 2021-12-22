package de.hofuniversity.minf.stundenplaner.service.boundary;

import de.hofuniversity.minf.stundenplaner.service.to.LessonTO;
import de.hofuniversity.minf.stundenplaner.service.to.TimeTableTO;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

public interface TimeTableService {

    List<LessonTO> findAllLessons();

    TimeTableTO findAllLessonsByVersion(Long versionId);

    TimeTableTO createTimeTable(TimeTableTO timeTableTO);

    TimeTableTO deleteTimeTable(Long versionId);

    LessonTO createSingleLesson(LessonTO lessonTO);

    LessonTO findSingleLesson(Long id);

    LessonTO deleteLesson(Long id);

    LessonTO updateLesson(Long id, LessonTO lessonTO);

    Workbook exportAll();

    Workbook exportVersion(Long versionId);


}
