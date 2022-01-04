package de.hofuniversity.minf.stundenplaner.service.criteria;


import de.hofuniversity.minf.stundenplaner.persistence.program.data.SemesterDO;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.data.LessonDO;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * @author KMP
 *
 */

public class CriterionRoomSize extends AbstractCriterion{

    public static final String CriterionName="CriterionRoomSize";

    private List<SemesterDO> semesterDOSeatList;
    private List<Integer> needSeatList;
    private List<Double> evaluationList;

    @Override
    public double evaluate(LessonDO lesson) {
        if(lesson.getRoomDO() == null) {
            return 100.0;
        }

        boolean pass = true;
        semesterDOSeatList = new ArrayList<>();
        needSeatList = new ArrayList<>();
        evaluationList = new ArrayList<>();


        List<SemesterDO> semesterDOList = lesson.getLectureDO().getSemesters();
        int availableSeats = lesson.getRoomDO( ).getSeats(); // Hier muessen diese Sitze von dem jeweiligen Raum rein
        if (availableSeats <= 0 ){
            return Double.NEGATIVE_INFINITY;
        }

        int requiredSeats; // Hier muessen die Sitze der Studenten rein
        int useForCompare;
        for (SemesterDO semesterDO:semesterDOList){
            // Alle SemesterDO sollen in den Raum einen Platz finden
            if (semesterDO.getActualParticipants() > semesterDO.getExpectedParticipants()){
                useForCompare = semesterDO.getActualParticipants();
            }else{
                useForCompare = semesterDO.getExpectedParticipants();
            }
            requiredSeats = useForCompare;
            if(availableSeats < requiredSeats){
                // Wenn zuwenig plaetze vorhanden sind
                pass = false;
                semesterDOSeatList.add((semesterDO));
                needSeatList.add(requiredSeats-availableSeats);
                evaluationList.add(Double.NEGATIVE_INFINITY);
                continue;
            }
            int tempInteger;
            double tempDouble;
            tempDouble = (double) availableSeats / requiredSeats;
            tempInteger = (int) (tempDouble * 90);
            if (tempInteger > 100){
                tempInteger = tempInteger % 100;
                semesterDOSeatList.add((semesterDO));
                needSeatList.add(0);
                evaluationList.add((double) (tempInteger * 2));
            }
        }
        if(evaluationList.size() <= 0){
            return Double.NEGATIVE_INFINITY;
        }
        if(!pass){
            return Double.NEGATIVE_INFINITY;
        }else{
            double sum = 0.0;
            for (double eachValueInList:evaluationList){
                sum = sum + eachValueInList;
            }
            return sum/evaluationList.size();
        }
    }



    @Override
    public CriterionExplaination createExplanation(LessonDO lesson, Double value) {

        CriterionExplaination criterionExplaination = new CriterionExplaination();
        criterionExplaination.criterionType = CriterionName;
        criterionExplaination.lesson = lesson;
        criterionExplaination.placeholders = new HashMap<>();

        int counter = 0;
        for (SemesterDO semesterDO:semesterDOSeatList){
            criterionExplaination.placeholders.put("Program Name", semesterDO.getProgram().getName());
            criterionExplaination.placeholders.put("Seats missing", Integer.toString(needSeatList.get(counter)));
            criterionExplaination.placeholders.put("Calculated Value", Double.toString((evaluationList.get(counter))));
            counter = counter +1;
        }
        criterionExplaination.weight = 100 - value;

        return criterionExplaination;
    }


}
