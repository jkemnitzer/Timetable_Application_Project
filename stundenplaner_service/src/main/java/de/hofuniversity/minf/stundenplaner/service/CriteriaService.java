package de.hofuniversity.minf.stundenplaner.service;

import de.hofuniversity.minf.stundenplaner.service.boundary.TimeTableService;
import de.hofuniversity.minf.stundenplaner.service.criteria.CriterionExplaination;

import java.util.List;

/**
 * @author mheckel
 * interface for modularization of service classes
 */
public interface CriteriaService {

    Double evaluateTimeTable (TimeTableService timeTableService, Long timeTableId);
    List<CriterionExplaination> explainTimeTable(TimeTableService timeTableService, Long timeTableId);

}
