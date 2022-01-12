package de.hofuniversity.minf.stundenplaner.service.criteria;

import de.hofuniversity.minf.stundenplaner.persistence.account.data.ModulePreferenceDO;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.data.LessonDO;

import java.util.HashMap;

public class CriterionModulesOfLecturer extends AbstractCriterion {
    public static final String CriterionName = "CriterionModulesOfLecturer";

    public double evaluate(LessonDO lesson) {
        if (lesson.getLectureDO() == null) {
            // If no lecturer is assigned to the lesson, this should not have a negative impact on the
            // score of the lesson
            return 100.0;
        }

        if (!lesson.getLecturerDO().getLecturerProfile().getFaculty().equals(lesson.getLectureDO().getPrimaryFaculty())) {
            return Double.NEGATIVE_INFINITY;
        }

        for (ModulePreferenceDO preference : lesson.getLecturerDO().getLecturerProfile().getModulePreferences()) {
            // Match based on the name because there might be lectures with different IDs but same name for
            // different semesters/years
            if (preference.getLecture().getName().equals(lesson.getLectureDO().getName())) {
                // Return the priority value the lecturer defined in its profile
                return Double.valueOf(preference.getPriority());
            }
        }
        return 0.0;
    }

    @Override
    public CriterionExplaination createExplanation(LessonDO lesson, Double value) {
        CriterionExplaination criterionExplaination = new CriterionExplaination();
        criterionExplaination.criterionType = CriterionName;
        criterionExplaination.lesson = lesson;
        criterionExplaination.weight = value;

        criterionExplaination.placeholders = new HashMap<>();
        // Text: "Lecturer $lecturerName wants to hold lecture $lectureName with $priority"
        criterionExplaination.placeholders.put("lecturerName", lesson.getLecturerDO().getUsername());
        criterionExplaination.placeholders.put("lectureName", lesson.getLectureDO().getName());

        for(ModulePreferenceDO preference: lesson.getLecturerDO().getLecturerProfile().getModulePreferences()) {
            if (preference.getLecture().getName().equals(lesson.getLectureDO().getName())) {
                criterionExplaination.placeholders.put("priority", Integer.toString(preference.getPriority()));
            }
        }

        return criterionExplaination;
    }
}