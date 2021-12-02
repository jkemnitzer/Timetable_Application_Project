package de.hofuniversity.minf.stundenplaner.service.criteria;

import de.hofuniversity.minf.stundenplaner.persistence.timetable.data.LessonDO;

import java.util.List;
import java.util.Map;

public class Criteria implements Criterion{

    private Map<Criterion, Long> criteria;

    @Override
    public double evaluate(List<LessonDO> lessonDOList) {
        return 0;
    }

    @Override
    public List<CriterionExplaination> explain(List<LessonDO> lessonDOList) {
        return null;
    }
}
