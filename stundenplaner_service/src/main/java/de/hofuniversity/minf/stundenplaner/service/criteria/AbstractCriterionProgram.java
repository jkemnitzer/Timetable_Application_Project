package de.hofuniversity.minf.stundenplaner.service.criteria;

import de.hofuniversity.minf.stundenplaner.persistence.program.data.SemesterDO;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.data.LessonDO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractCriterionProgram implements Criterion{
    private Map<String, List<LessonDO>> getLessonsByCourse(List<LessonDO> lessonDOList) {
        Map<String, List<LessonDO>> lessonsPerCourse = new HashMap<>();

        for (LessonDO lesson : lessonDOList) {
            for (SemesterDO semester : lesson.getLectureDO().getSemesters()) {
                String identifier = semester.getProgram().getName().concat(semester.getNumber());
                boolean found = false;
                for (String key : lessonsPerCourse.keySet()) {
                    if (key.equals(identifier)) {
                        found = true;
                        lessonsPerCourse.get(key).add(lesson);
                    }
                }

                if (!found) {
                    List<LessonDO> list = new ArrayList<>();
                    list.add(lesson);
                    lessonsPerCourse.put(identifier, list);
                }
            }
        }

        return lessonsPerCourse;
    }


    @Override
    public Double evaluate(List<LessonDO> lessonDOList) {
        List<Double> weights = new ArrayList<>();
        List<Double> values = new ArrayList<>();
        Map<String, List<LessonDO>> lessonsPerCourse = getLessonsByCourse(lessonDOList);

        for(String identifier: lessonsPerCourse.keySet()) {
            weights.add(1.0);
            values.add(evaluateLessonsPerCourse(lessonsPerCourse.get(identifier)));
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
        Map<String, List<LessonDO>> lessonsPerCourse = getLessonsByCourse(lessonDOList);

        for(String identifier: lessonsPerCourse.keySet()) {
            weights.add(1.0);
            values.add(evaluateLessonsPerCourse(lessonsPerCourse.get(identifier)));
        }

        weights = Criteria.deLinearizeWeights(weights, values);

        double sum = 0.0;

        int i = 0;
        for(String identifier: lessonsPerCourse.keySet()) {
            Double value = values.get(i) * weights.get(i);
            if (value < 100.0) {
                for(LessonDO lesson: lessonsPerCourse.get(identifier)) {
                    results.add(createExplanation(lesson,value,identifier));
                }
            }
            i++;
        }
        return results;
    }

    public abstract Double evaluateLessonsPerCourse(List<LessonDO> lessonDOList);
    public abstract CriterionExplaination createExplanation(LessonDO lesson, Double value, String identifier);

}
