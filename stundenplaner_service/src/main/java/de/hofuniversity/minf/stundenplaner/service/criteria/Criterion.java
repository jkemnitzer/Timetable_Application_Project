package de.hofuniversity.minf.stundenplaner.service.criteria;

import de.hofuniversity.minf.stundenplaner.persistence.timetable.data.LessonDO;
import de.hofuniversity.minf.stundenplaner.service.to.TimeTableTO;

import java.util.List;

public interface Criterion {
    Double evaluate(List<LessonDO> lessonDOList);
    List<CriterionExplaination> explain(List<LessonDO> lessonDOList);

}
