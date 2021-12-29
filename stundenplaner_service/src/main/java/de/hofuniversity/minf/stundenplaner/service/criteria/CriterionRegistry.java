package de.hofuniversity.minf.stundenplaner.service.criteria;

import de.hofuniversity.minf.stundenplaner.common.exception.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CriterionRegistry {
    private Map<String, Criterion> criteriaList;
    private List<Criterion> allCriteria;

    public CriterionRegistry() {
        criteriaList = new HashMap<>();

        // Add criteria and their string representation using the following Syntax:
        //criteriaList.put(RoomCriteria.CriterionName, new RoomCriteria());


        criteriaList.put(CriteriaRoom.CriterionName, new CriteriaRoom());
        criteriaList.put(CriterionTimePreferenceLecturer.CriterionName, new CriterionTimePreferenceLecturer());
        criteriaList.put(CriterionModulesOfLecturer.CriterionName, new CriterionModulesOfLecturer());

        allCriteria = new ArrayList<>();
        for(Criterion criterion: criteriaList.values()) {
            allCriteria.add(criterion);
        }
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
