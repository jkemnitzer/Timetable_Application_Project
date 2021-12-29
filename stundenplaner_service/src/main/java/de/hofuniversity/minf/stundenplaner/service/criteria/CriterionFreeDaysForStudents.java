package de.hofuniversity.minf.stundenplaner.service.criteria;

import de.hofuniversity.minf.stundenplaner.persistence.account.data.ModulePreferenceDO;
import de.hofuniversity.minf.stundenplaner.persistence.program.data.SemesterDO;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.data.LessonDO;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.timeslot.data.TimeslotDO;

import java.util.*;

public class CriterionFreeDaysForStudents implements Criterion {
    public static final String CriterionName = "CriterionFreeDaysForStudents";

    Map<String, List<LessonDO>> getLessonsByCourse(List<LessonDO> lessonDOList) {
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

    private Double evaluateLessonsPerCourse(List<LessonDO> lessonDOList) {
        Double value = 0.0;

        Set<TimeslotDO> usedTimeSlots = new HashSet<>();
        int[] lessonsPerDay = {0, 0, 0, 0, 0, 0};

        for(LessonDO lesson: lessonDOList) {
            for(TimeslotDO timeSlot: usedTimeSlots) {
                if(lesson.getTimeslotDO().equals(timeSlot)) {
                    // Timeslot is already used, so the combination is invalid
                    return Double.NEGATIVE_INFINITY;
                }
                usedTimeSlots.add(lesson.getTimeslotDO());
                int idx = lesson.getTimeslotDO().getWeekdayNr();
                if(idx < 0 || idx >= 6) {
                    // Weekday has to be 0 at least. Because there are no lectures sundays, it should be smaller
                    // than 6
                    return Double.NEGATIVE_INFINITY;
                }
                lessonsPerDay[idx] += 1;
            }
        }

        int freeDays = 0;
        for(int i = 0; i < 6; i++) {
            if(lessonsPerDay[i] == 0) {
                freeDays++;
            }
        }

        // Optimum at two free days per week
        value = (100.0/2.0) * freeDays;
        if (value > 100.0) {
            value = 100.0;
        }

        return value;
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
                    CriterionExplaination criterionExplaination = new CriterionExplaination();

                    criterionExplaination.criterionType = CriterionName;
                    criterionExplaination.lesson = lesson;
                    criterionExplaination.weight = value;

                    criterionExplaination.placeholders = new HashMap<>();
                    // Text: "Course $courseName has too few free days per week"
                    criterionExplaination.placeholders.put("courseName", identifier);

                    results.add(criterionExplaination);
                }
            }
            i++;
        }

        return results;
    }
}
