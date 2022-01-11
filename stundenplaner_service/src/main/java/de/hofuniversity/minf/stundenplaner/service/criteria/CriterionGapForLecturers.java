package de.hofuniversity.minf.stundenplaner.service.criteria;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hofuniversity.minf.stundenplaner.persistence.timetable.data.LessonDO;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.timeslot.data.TimeslotDO;
import de.hofuniversity.minf.stundenplaner.persistence.user.data.UserDO;

/**
 * Criterion which ensures gaps for lecturers so they don't have days which are
 * completely full with lectures
 * 
 */
public class CriterionGapForLecturers extends AbstractCriterionLecturer {

    public static final String CriterionName = "CriterionGapForLecturers";

    // Limit for consecutive lessons per day
    private static final int CONSECUTIVE_LESSONS_LIMIT = 4;

    private Map<LessonDO, Integer> consecutiveLessons;

    public CriterionGapForLecturers() {
        cleanupBeforeEvaluation();
    }

    private void AddLessonsToMap(List<LessonDO> lessons, int value) {
        for(LessonDO lesson: lessons) {
            consecutiveLessons.put(lesson, value);
        }
    }

    @Override
    public Double evaluateLessonsPerIdentifier(List<LessonDO> lessonDOList) {
        // sort lessons by weekday, then start time
        lessonDOList.sort(
                (first, second) -> {
                    TimeslotDO firstTimeslot = first.getTimeslotDO();
                    TimeslotDO secondTimeslot = second.getTimeslotDO();
                    int result = firstTimeslot.getWeekdayNr().compareTo(secondTimeslot.getWeekdayNr());
                    if (result == 0) {
                        result = firstTimeslot.getStart().compareTo(secondTimeslot.getStart());
                    }
                    return result;
                });

        int consecutiveLessonsMaximum = 0;

        // initialize local variables for counting a streak of consecutive lessons:
        List<LessonDO> consecutives = new ArrayList<>();
        double value = 100.0;

        for (int i = 1; i < lessonDOList.size(); i++) {
            if (isConsecutive(lessonDOList.get(i-1), lessonDOList.get(i))) {
                // Add the lesson to the list of consecutive lessons. If the
                // list is empty (this is the first pair), add the first lesson
                // as well. Otherwise, add only the next lesson (because the first
                // lesson of now was already added before).
                if(consecutives.size() == 0) {
                    consecutives.add(lessonDOList.get(i-1));
                }
                consecutives.add(lessonDOList.get(i));
            } else {
                // Two lessons are not consecutive, so the list of consecutive
                // lessons can be evaluated

                if(consecutiveLessons.size() > CONSECUTIVE_LESSONS_LIMIT) {
                    value = Double.NEGATIVE_INFINITY;
                }

                if (consecutiveLessons.size() > consecutiveLessonsMaximum) {
                    consecutiveLessonsMaximum = consecutiveLessons.size();
                }

                // scoring gets progressively worse, the more consecutive lessons there are (but
                // still remains positive). For the calculation, we use the day with the maximum
                // consecutive lessons of the week:
                double newValue = 100D - (consecutiveLessonsMaximum * (100D / (CONSECUTIVE_LESSONS_LIMIT + 1)));

                if(newValue < value) {
                    // Assume the worst value
                    value = newValue;
                }

                AddLessonsToMap(consecutives, consecutives.size());

                consecutives = new ArrayList<>();
            }

            AddLessonsToMap(consecutives, consecutives.size());
        }

        return value;
    }

    @Override
    public CriterionExplaination createExplanation(LessonDO lesson, Double value, UserDO identifier) {
        CriterionExplaination criterionExplaination = new CriterionExplaination();
        criterionExplaination.criterionType = CriterionName;
        criterionExplaination.lesson = lesson;
        criterionExplaination.weight = value;
        criterionExplaination.placeholders = new HashMap<>();
        // Text: "Lesson $lesson in in a block of $blocksize lessons for lecturer $lecturer"
        criterionExplaination.placeholders.put("lesson", lesson.getLectureDO().getName());
        if(consecutiveLessons.containsKey(lesson)) {
            criterionExplaination.placeholders.put("blocksize", Integer.toString(consecutiveLessons.get(lesson)));
        }
        criterionExplaination.placeholders.put("lecturer", identifier.getFirstName() + " " + identifier.getLastName());
        return criterionExplaination;
    }

    @Override
    public void cleanupBeforeEvaluation() {
        consecutiveLessons = new HashMap<>();
    }

    /**
     * Returns true if two lessons are consecutive, e.g. the second lesson starts
     * right after the first ends (excluding break time). This only handles "normal"
     * lessons, might need to implement a fallback method for "unusual" lessons such
     * as saturday lectures.
     * 
     * @param first  The first LessonDO to compare
     * @param second The second LessonDO to compare
     * @return true if two lessons are consecutive. The lessons before and after mid
     *         day pause are treated as consecutive.
     */
    private boolean isConsecutive(LessonDO first, LessonDO second) {
        // if not on the same day we can always return false
        if (!first.getTimeslotDO().getWeekdayNr().equals(second.getTimeslotDO().getWeekdayNr())) {
            return false;
        }
        // handle mid day pause:
        if (first.getTimeslotDO().getStart().equals(LocalTime.of(11, 30))) {
            return second.getTimeslotDO().getStart().equals(LocalTime.of(14, 0));
        } else {
            // handle normal lectures:
            return first.getTimeslotDO().getEnd().plusMinutes(15).equals(second.getTimeslotDO().getStart());
        }
    }

}