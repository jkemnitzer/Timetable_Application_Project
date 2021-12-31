package de.hofuniversity.minf.stundenplaner.service.criteria;

import de.hofuniversity.minf.stundenplaner.persistence.timetable.data.LessonDO;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.timeslot.data.TimeslotDO;

import java.util.*;

public class CriterionFreeDaysForStudents extends AbstractCriterionProgram {
    public static final String CriterionName = "CriterionFreeDaysForStudents";


    @Override
    public Double evaluateLessonsPerCourse(List<LessonDO> lessonDOList) {
        double value;

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
    public CriterionExplaination createExplanation(LessonDO lesson, Double value, String identifier) {
        CriterionExplaination criterionExplaination = new CriterionExplaination();

        criterionExplaination.criterionType = CriterionName;
        criterionExplaination.lesson = lesson;
        criterionExplaination.weight = value;

        criterionExplaination.placeholders = new HashMap<>();
        // Text: "Course $courseName has too few free days per week"
        criterionExplaination.placeholders.put("courseName", identifier);

        return criterionExplaination;
    }

    @Override
    public void cleanup() {

    }

}
