package de.hofuniversity.minf.stundenplaner.service.criteria;

import de.hofuniversity.minf.stundenplaner.persistence.account.data.ModulePreferenceDO;
import de.hofuniversity.minf.stundenplaner.persistence.room.data.FeatureDO;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.data.LessonDO;


import java.util.HashMap;
import java.util.List;


/**
 * @author KMP
 *
 */

public class CriterionRoom extends AbstractCriterion{

    public static final String CriterionName="CriterionRoom";



    @Override
    public double evaluate(LessonDO lesson) {
        if (lesson.getLecturerDO() == null || lesson.getRoomDO() == null) {
            return 100.0;
        }

        // Falls eine Liste an Feature gebraucht wird muss in UserDO/UserTo mit List<String> gearbeitet werden
        int listCounter = 0; // zählt wieviel Feature später übereinstimmen müssen
        String roomFeatureRequirement = "";
        Long lessonID;
        lessonID = lesson.getLectureDO().getId();

        List<ModulePreferenceDO> modulePreferenceDOList = lesson.getLecturerDO().getLecturerProfile().getModulePreferences();
        for (ModulePreferenceDO modulePreferenceDO:modulePreferenceDOList){
            if (lessonID.equals(modulePreferenceDO.getLecture().getId())){
                roomFeatureRequirement = modulePreferenceDO.getRoomFeatureRequirement();
                if (roomFeatureRequirement.equals("")){
                    return 100.0;
                }else{
                    listCounter = listCounter + 1;
                    break; // Da nur eine Requirement vorhanden ist, kann man die Schleife sofort abbrechen...
                }
            }
        }


        List<FeatureDO> featureDOList = lesson.getRoomDO().getFeatureList();
        for (FeatureDO featureDo:featureDOList){
            String feature = featureDo.getFeature();
            // Hier kommt die Bedingung, welches mit dem Lecturer angegebenen Anforderung übereinstimmen soll
            if(roomFeatureRequirement.equals(feature)){
                listCounter = listCounter - 1;
            }
        }

        if (listCounter <= 0){
            //Wenn alle Bedingungen zum Raum mindestens erfüllt wurde dann...
            return 100.0;
        }else{
            return Double.NEGATIVE_INFINITY;
        }
    }

    @Override
    public CriterionExplaination createExplanation(LessonDO lesson, Double value) {

        CriterionExplaination criterionExplaination = new CriterionExplaination();
        criterionExplaination.criterionType = CriterionName;
        criterionExplaination.lesson = lesson;
        criterionExplaination.placeholders = new HashMap<>();
        criterionExplaination.placeholders.put("LecturerName", lesson.getLecturerDO().getUsername());
        criterionExplaination.placeholders.put("LectureName", lesson.getLectureDO().getName());

        for (ModulePreferenceDO modulePreferenceDO : lesson.getLecturerDO().getLecturerProfile().getModulePreferences()) {
            if (modulePreferenceDO.getLecture().getId().equals(lesson.getLectureDO().getId())) {
                criterionExplaination.placeholders.put("RoomRequirement", modulePreferenceDO.getRoomFeatureRequirement());
            }
        }
        criterionExplaination.weight = 100 - value;
        return criterionExplaination;

    }


}
