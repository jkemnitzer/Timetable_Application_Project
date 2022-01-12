package de.hofuniversity.minf.stundenplaner.service.criteria;

import de.hofuniversity.minf.stundenplaner.persistence.timetable.data.LessonDO;

import java.util.List;

/**
 * @author KMP
 *
 */

public interface Criterion {

    double evaluate(List<LessonDO> lessonDOList);

    List<CriterionExplaination> explain(List<LessonDO> lessonDOList);

}
