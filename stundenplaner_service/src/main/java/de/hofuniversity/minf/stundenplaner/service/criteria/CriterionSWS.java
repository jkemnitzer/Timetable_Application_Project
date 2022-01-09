package de.hofuniversity.minf.stundenplaner.service.criteria;

import de.hofuniversity.minf.stundenplaner.persistence.timetable.data.LessonDO;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.timeslot.data.TimeslotDO;
import de.hofuniversity.minf.stundenplaner.persistence.user.data.UserDO;

import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;


/**
 * The SWSCriterion defines the criterion for evaluating a lecturer regarding the work hours of a lecture.
 *
 * It consists out of two-borders and therefore three fields:
 *  - work hours below the lower-border are wanted; A = {100.0}
 *  - work hours above the lower-border and below the upper-border are unwanted; A = {100.0, ..., 0.0}
 *  - work hours above the upper-border are  unwanted; A = {-INF}
 *
 * @author Jan Gaida
 * @task #6255
 */
public class CriterionSWS extends AbstractCriterionLecturer {

    // the name of this criterion
    public static final String CriterionName = "CriterionSWS";

    // the two defined border-values to calculate the evaluation with, based on feedback
    private static final double LOWER_BORDER_OVERTIME = 2.0;
    private static final double UPPER_BORDER_OVERTIME = 6.0;

    // as a work-around to give a solid explanation we'll store the calculated values instead of calculating them multiple times
    private HashMap<Long, Integer> mappedOvertimeSws = new HashMap<>();

    @Override
    public Double evaluateLessonsPerIdentifier(List<LessonDO> lessonsToEvaluate) {
        // skip if no lessons
        if (lessonsToEvaluate.size() == 0) { return 100.0; }

        // calculate current applied-sws
        int appliedSws = 0;
        for (LessonDO lesson : lessonsToEvaluate) {
            switch (lesson.getLessonType()) {
                case LECTURE, ONLINE_LECTURE, EXERCISE -> {
                    appliedSws += calculateSwsLecturerBased(lesson.getTimeslotDO());
                    break;
                }
                default -> {
                    // appliedSws += 0; // by default, we don't assume any sws
                }
            }
        }
        int overtimeSws = appliedSws - lessonsToEvaluate.get(0).getLecturerDO().getLecturerProfile().getSws();
        this.mappedOvertimeSws.put(lessonsToEvaluate.get(0).getLecturerDO().getId(), Math.round(overtimeSws));

        // evaluate
        if (overtimeSws <= LOWER_BORDER_OVERTIME) {
            return 100.0; // all fine
        } else if (overtimeSws >= UPPER_BORDER_OVERTIME){
            return Double.NEGATIVE_INFINITY; // too much sws to given lecturer
        } else {
            // i.e. lower-border = 2 && upper-border = 6
            // overtimeSws: 3 ==> 75
            // overtimeSws: 4 ==> 50
            // overtimeSws: 5 ==> 25
            return 100. * (1 - (overtimeSws - LOWER_BORDER_OVERTIME) / (UPPER_BORDER_OVERTIME - LOWER_BORDER_OVERTIME));
        }
    }

    /**
     * (Rounded) Formula to calculate the SWS-amount for a lecture.
     * => 1 SWS per 45min lecture-time
     *
     * i.e.:
     *  45min lesson = 1 SWS
     *  90min lesson = 2 SWS
     * 110min lesson = 2 SWS
     * 115min lesson = 3 SWS
     * 135min lesson = 3 SWS
     *
     * @param timeslot the timeslot for given lesson
     *
     * @return the sws-amount for given lesson
     */
    private long calculateSwsLecturerBased(TimeslotDO timeslot) {
        long timeslotMinutes = ChronoUnit.MINUTES.between(timeslot.getStart(), timeslot.getEnd());
        return Math.round(timeslotMinutes/45.0);
    }

    @Override
    public CriterionExplaination createExplanation(LessonDO lesson, Double value, UserDO lecturer) {
        CriterionExplaination criterionExplaination = new CriterionExplaination();
        criterionExplaination.criterionType = CriterionName;
        criterionExplaination.lesson = lesson;
        criterionExplaination.weight = value;

        criterionExplaination.placeholders = new HashMap<>();
        // Text: "Lecturer $lecturerName has $appliedSws overdue his sws-preferences."
        criterionExplaination.placeholders.put("lecturerName", lesson.getLecturerDO().getUsername());
        criterionExplaination.placeholders.put("$appliedSws", String.valueOf(this.mappedOvertimeSws.get(lecturer.getId())));
        return criterionExplaination;
    }

    @Override
    public void cleanupBeforeEvaluation() {
        // clean the saved overtimeSws
        mappedOvertimeSws = new HashMap<>();
    }
}
