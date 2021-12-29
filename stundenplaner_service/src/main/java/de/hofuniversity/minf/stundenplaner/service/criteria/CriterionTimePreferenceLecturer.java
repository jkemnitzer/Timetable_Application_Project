package de.hofuniversity.minf.stundenplaner.service.criteria;

import de.hofuniversity.minf.stundenplaner.persistence.account.data.ModulePreferenceDO;
import de.hofuniversity.minf.stundenplaner.persistence.account.data.TimeSlotPreferenceDO;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.data.LessonDO;

import java.util.HashMap;

public class CriterionTimePreferenceLecturer extends AbstractCriterion {
    public static final String CriterionName = "CriterionTimePreferenceLecturer";

    public Double evaluate(LessonDO lesson) {
        if (lesson.getTimeslotDO() == null) {
            // If no timeslot is assigned to the lesson, this should not have a negative impact on the
            // score of the lesson
            return 100.0;
        }

        for (TimeSlotPreferenceDO preference : lesson.getLecturerDO().getLecturerProfile().getTimeSlotPreferences()) {
            if (preference.getTimeSlot().getId().equals(lesson.getTimeslotDO().getId())) {
                if (preference.getForced() && preference.getPriority() == 0.0) {
                    return Double.NEGATIVE_INFINITY;
                }

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
        // Text: "Lecturer $lecturerName wants to hold lecture $lectureName at $timeslot with $priority ($forced)"
        criterionExplaination.placeholders.put("lecturerName", lesson.getLecturerDO().getUsername());
        criterionExplaination.placeholders.put("lectureName", lesson.getLectureDO().getName());

        for (TimeSlotPreferenceDO preference : lesson.getLecturerDO().getLecturerProfile().getTimeSlotPreferences()) {
            if (preference.getId().equals(lesson.getTimeslotDO().getId())) {
                criterionExplaination.placeholders.put("priority", Integer.toString(preference.getPriority()));

                if(preference.getForced()) {
                    criterionExplaination.placeholders.put("forced", "enforced");
                } else {
                    criterionExplaination.placeholders.put("forced", "not enforced");
                }
            }
        }

        criterionExplaination.placeholders.put("timeslot", lesson.getTimeslotDO().getWeekdayNr().toString() + "(" + lesson.getTimeslotDO().getStart().toString() + " - " + lesson.getTimeslotDO().getEnd().toString() + ")");

        return criterionExplaination;
    }
}
