package de.hofuniversity.minf.stundenplaner.service.criteria;

import de.hofuniversity.minf.stundenplaner.common.exception.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CriterionRegistry {
    private final Map<String, Criterion> criteriaList;
    private final List<Criterion> allCriteria;

    public CriterionRegistry() {
        criteriaList = new HashMap<>();

        // Add criteria and their string representation using the following Syntax:
        //criteriaList.put(RoomCriteria.CriterionName, new RoomCriteria());
        criteriaList.put(CriterionSWS.CriterionName, new CriterionSWS());
        criteriaList.put(CriterionRoom.CriterionName, new CriterionRoom());
        criteriaList.put(CriterionTimePreferenceLecturer.CriterionName, new CriterionTimePreferenceLecturer());
        criteriaList.put(CriterionModulesOfLecturer.CriterionName, new CriterionModulesOfLecturer());
        criteriaList.put(CriterionFreeDaysForStudents.CriterionName, new CriterionFreeDaysForStudents());
        criteriaList.put(CriterionLocationChange.CriterionName, new CriterionLocationChange());
        criteriaList.put(CriterionRoomSize.CriterionName, new CriterionRoomSize());
        criteriaList.put(CriterionAccessibility.CriterionName, new CriterionAccessibility());
        criteriaList.put(CriterionOnlineLecture.CriterionName, new CriterionOnlineLecture());



        allCriteria = new ArrayList<>();
        allCriteria.addAll(criteriaList.values());
    }

    public Criterion getCriterion(String type) throws NotFoundException {
        if(criteriaList.containsKey(type)) {
            return criteriaList.get(type);
        }
        throw new NotFoundException(CriterionRegistry.class, 0L);
    }

    public List<Criterion> getAllCriteria() {
        return allCriteria;
    }
}
