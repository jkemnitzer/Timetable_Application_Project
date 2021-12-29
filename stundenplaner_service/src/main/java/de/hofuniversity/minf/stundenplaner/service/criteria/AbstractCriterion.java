package de.hofuniversity.minf.stundenplaner.service.criteria;

import de.hofuniversity.minf.stundenplaner.persistence.timetable.data.LessonDO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractCriterion implements Criterion{
    public static final String CriterionName = "CriterionFreeDaysForStudents";
    @Override
    public Double evaluate(List<LessonDO> lessonDOList) {
        List<Double> weights = new ArrayList<>();
        List<Double> values = new ArrayList<>();

        for(LessonDO lesson: lessonDOList) {
            weights.add(1.0);
            values.add(evaluate(lesson));
        }

        weights = Criteria.deLinearizeWeights(weights, values);

        double sum = 0.0;

        for(int i = 0; i < values.size(); i++) {
            sum += values.get(i) * weights.get(i);
        }

        sum /= values.size();

        return sum;
    }

    @Override
    public List<CriterionExplaination> explain(List<LessonDO> lessonDOList) {
        List<CriterionExplaination> results = new ArrayList<>();
        List<Double> weights = new ArrayList<>();
        List<Double> values = new ArrayList<>();

        for(LessonDO lesson: lessonDOList) {
            weights.add(1.0);
            values.add(evaluate(lesson));
        }

        weights = Criteria.deLinearizeWeights(weights, values);

        double sum = 0.0;

        for(int i = 0; i < values.size(); i++) {
            Double value = values.get(i) * weights.get(i);
            if (value < 100.0) {
                CriterionExplaination criterionExplaination = createExplanation(lessonDOList.get(i), value);
                results.add(criterionExplaination);
            }
        }

        return results;
    }

    public abstract Double evaluate(LessonDO lesson);
    public abstract CriterionExplaination createExplanation(LessonDO lesson, Double value);
}
