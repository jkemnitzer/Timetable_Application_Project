package de.hofuniversity.minf.stundenplaner.service.criteria;

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

        allCriteria = new ArrayList<>();
        for(Criterion criterion: criteriaList.values()) {
            allCriteria.add(criterion);
        }
    }

    public Criterion getCriterion(String type) {
        if(criteriaList.containsKey(type)) {
            return criteriaList.get(type);
        } else {
            return null;
        }
    }

    public List<Criterion> getAllCriteria() {
        return allCriteria;
    }
}
