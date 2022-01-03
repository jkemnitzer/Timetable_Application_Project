package de.hofuniversity.minf.stundenplaner.service.criteria;

import de.hofuniversity.minf.stundenplaner.persistence.program.data.SemesterDO;
import de.hofuniversity.minf.stundenplaner.persistence.room.data.FeatureDO;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.data.LessonDO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author KMP
 *
 */


public class CriterionAccessibility extends AbstractCriterion {

    public static final String CriterionName = "CriterionAccessibility";

    String evaluationStringLine = null;

    //Declaration for more specific accessibility, that can be featured in RoomFeatures!
    boolean hasAccessibility; //first, only general accessibility for wheelchair people
    // boolean hasViewAccessibility; // distance for people with eye/view problems
    // boolean hasWheelChairAccessibility; // may be in combination with ViewAccessibility
    // boolean hasWheelChairComputerAccessibility; // example: can a person have seat in front of a computer with wheelchair?

    @Override
    public double evaluate(LessonDO lesson) {
        List<SemesterDO> semesterDOList = lesson.getLectureDO().getSemesters();
        List<Double> calculationList = new ArrayList<>();

        hasAccessibility = checkAccessibilityRoomFeature("Accessibility", lesson);

        for (SemesterDO semesterDO : semesterDOList) {
            if (semesterDO.isAccessibility_needed() && !hasAccessibility){
                    return Double.NEGATIVE_INFINITY;
            }
            calculationList.add(100.0);
        }

        return 100.0;
    }

    @Override
    public CriterionExplaination createExplanation (LessonDO lesson, Double value){

        CriterionExplaination criterionExplaination = new CriterionExplaination();
        criterionExplaination.criterionType = CriterionName;
        criterionExplaination.lesson = lesson;
        criterionExplaination.placeholders = new HashMap<>();

        hasAccessibility = checkAccessibilityRoomFeature("Accessibility",lesson);
        writeToStringline(lesson.getRoomDO().getRoomNumber()); // ini
        List<SemesterDO> semesterDOList = lesson.getLectureDO().getSemesters();

        for (SemesterDO semesterDO : semesterDOList) {
            if (semesterDO.isAccessibility_needed()){
                // need Accessibility (general!)
                if (hasAccessibility){
                    writeToStringline(semesterDO.getProgram().getName()+":has");
                }else {
                    writeToStringline(semesterDO.getProgram().getName()+":need");
                }
                continue;
            }
            writeToStringline(semesterDO.getProgram().getName()+":noneed");
        }
        criterionExplaination.placeholders.put("RoomAccessibility", lesson.getRoomDO().getRoomNumber());
        criterionExplaination.placeholders.put("GeneralAccessibilityRequirement", evaluationStringLine);
        criterionExplaination.weight = 100 - value;
        return criterionExplaination;

    }

    private boolean checkAccessibilityRoomFeature(String strFeature, LessonDO lesson){
        List<FeatureDO> featureDOList = lesson.getRoomDO().getFeatureList();
        for (FeatureDO featureDO : featureDOList) {
            // Get all featured accessibility from this room...
            if (featureDO.getFeature().equals(strFeature)) {
                return true;
            }
            // Hier können weitere accessibility festgelegt werden.
        }
        return false;
    }


    private void writeToStringline (String str){

        if (evaluationStringLine == null) {
            evaluationStringLine = "Room Number:" + str + ",";
        }else {
            evaluationStringLine = evaluationStringLine + str + ",";
        }
    }


}
