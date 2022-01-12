package de.hofuniversity.minf.stundenplaner.service.criteria;

import de.hofuniversity.minf.stundenplaner.persistence.timetable.data.LessonDO;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.timeslot.data.TimeslotDO;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author KMP
 *
 */



public class CriterionLocationChange extends AbstractCriterionProgram{

    public static final String CriterionName="CriterionLocationChange";

    private List<List<LessonDO>> collisions;

    public CriterionLocationChange(){
        cleanupBeforeEvaluation();
    }

    @Override
    public void cleanupBeforeEvaluation() {
        collisions = new ArrayList<>();
    }

    @Override
    public Double evaluateLessonsPerIdentifier(List<LessonDO> lessonDOList) {

        Map<TimeslotDO, LessonDO> lessonMap = new HashMap<>();
        List<TimeslotDO> timeslotDOList = new ArrayList<>();

        for (LessonDO lessonDO : lessonDOList) {
            lessonMap.put(lessonDO.getTimeslotDO(), lessonDO);
            timeslotDOList.add(lessonDO.getTimeslotDO());
        }

        Collections.sort(timeslotDOList);

        LessonDO lastLesson = null;
        double value = 100.0;
        for(TimeslotDO timeslotDO:timeslotDOList){
            if(lastLesson == null){
                lastLesson = lessonMap.get(timeslotDO);
                continue;
            }
            if (lastLesson.getRoomDO().getLocation().equals(lessonMap.get(timeslotDO).getRoomDO().getLocation())){
                lastLesson = lessonMap.get(timeslotDO);
                continue;
            }
            if (!(lastLesson.getTimeslotDO().getWeekdayNr().equals(timeslotDO.getWeekdayNr()))){
                lastLesson = lessonMap.get(timeslotDO);
                continue;
            }
            Duration duration = Duration.between(lastLesson.getTimeslotDO().getEnd(), timeslotDO.getStart());

            if (duration.getSeconds() >= 3600){
                lastLesson = lessonMap.get(timeslotDO);
                continue;
            }
            List<LessonDO> collision = new ArrayList<>();
            collision.add(lastLesson);
            collision.add(lessonMap.get(timeslotDO));
            collisions.add(collision);
            value = Double.NEGATIVE_INFINITY;
        }
        return value;
    }

    @Override
    public CriterionExplaination createExplanation(LessonDO lesson, Double value, String identifier) {
        CriterionExplaination criterionExplaination = null;

        for(List<LessonDO> collision:collisions){
            if(collision.get(0).equals(lesson)|| collision.get(1).equals(lesson)){

                criterionExplaination = new CriterionExplaination();
                criterionExplaination.criterionType = CriterionName;
                criterionExplaination.lesson = lesson;
                criterionExplaination.weight = value;

                criterionExplaination.placeholders = new HashMap<>();
                // Text: "Break after this lesson is not long enough to travel from $firstlocation to $secondlocation"
                criterionExplaination.placeholders.put("firstlocation", collision.get(0).getRoomDO().getLocation());
                criterionExplaination.placeholders.put("secondlocation", collision.get(1).getRoomDO().getLocation());
            }
        }
        return criterionExplaination;
    }
}
