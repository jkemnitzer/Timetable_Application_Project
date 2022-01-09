package de.hofuniversity.minf.stundenplaner.service.criteria;

import de.hofuniversity.minf.stundenplaner.persistence.timetable.data.LessonDO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractCriterionMapped<T> implements Criterion{
    @Override
    public double evaluate(List<LessonDO> lessonDOList) {
        cleanupBeforeEvaluation();
        List<Double> weights = new ArrayList<>();
        List<Double> values = new ArrayList<>();
        Map<T, List<LessonDO>> lessonsPerCourse = getLessonMap(lessonDOList);

        for(T identifier: lessonsPerCourse.keySet()) {
            weights.add(1.0);
            values.add(evaluateLessonsPerIdentifier(lessonsPerCourse.get(identifier)));
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
        cleanupBeforeEvaluation();
        List<CriterionExplaination> results = new ArrayList<>();
        List<Double> weights = new ArrayList<>();
        List<Double> values = new ArrayList<>();
        Map<T, List<LessonDO>> lessonsPerCourse = getLessonMap(lessonDOList);

        for(T identifier: lessonsPerCourse.keySet()) {
            weights.add(1.0);
            values.add(evaluateLessonsPerIdentifier(lessonsPerCourse.get(identifier)));
        }

        weights = Criteria.deLinearizeWeights(weights, values);

        int i = 0;
        for(T identifier: lessonsPerCourse.keySet()) {
            double value = values.get(i) * weights.get(i);
            if (value < 100.0) {
                for(LessonDO lesson: lessonsPerCourse.get(identifier)) {
                    CriterionExplaination criterionExplaination = createExplanation(lesson,value,identifier);
                    if (criterionExplaination != null){
                        results.add(criterionExplaination);
                    }
                }
            }
            i++;
        }
        return results;
    }

    /**
     * Maps given list of lessons to it's identifier
     *
     * @param lessons the list of lesson to be evaluated
     *
     * @return map containing the list of lesson (value) held by an identifier of type T (key)
     */
    public abstract Map<T, List<LessonDO>> getLessonMap(List<LessonDO> lessons);

    /**
     * Similar to AbstractCriterion this is called to calculate the evaluation-value based on given list of lessons
     *
     * @param lessonDOList the list of lesson to be evaluated
     *
     * @return the evaluation-value
     */
    public abstract Double evaluateLessonsPerIdentifier(List<LessonDO> lessonDOList);

    /**
     * Similar to AbstractCriterion this is called to explain the evaluation-value based on given parameters
     *
     * @param lesson the lesson-object which got evaluated
     * @param value the evaluation-value
     * @param identifier the lecture from whom the evaluation is based
     *
     * @return the explanation-object
     */
    public abstract CriterionExplaination createExplanation(LessonDO lesson, Double value, T identifier);

    /**
     * Called before the Criterion is calculated
     */
    public abstract void cleanupBeforeEvaluation();
}
