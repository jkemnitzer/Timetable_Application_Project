package de.hofuniversity.minf.stundenplaner.rest;

import de.hofuniversity.minf.stundenplaner.service.CriteriaService;
import de.hofuniversity.minf.stundenplaner.service.boundary.TimeTableService;
import de.hofuniversity.minf.stundenplaner.service.criteria.CriterionExplaination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("criteria")
public class CriteriaController {
    private final CriteriaService criteriaService;
    private final TimeTableService timeTableService;

    @Autowired
    public CriteriaController(CriteriaService criteriaService, TimeTableService timeTableService) {
        this.criteriaService = criteriaService;
        this.timeTableService = timeTableService;
    }

    @GetMapping("/evaluate/{timeTableId}")
    public ResponseEntity<Double> evaluateTimeTable(
            @PathVariable("timeTableId") Long timeTableId
    ) {
        return ResponseEntity.ok(criteriaService.evaluateTimeTable(timeTableService, timeTableId));
    }

    @GetMapping("/explain/{timeTableId}")
    public ResponseEntity<List<CriterionExplaination>> explainTimeTable(
            @PathVariable("timeTableId") Long timeTableId
    ) {
        return ResponseEntity.ok(criteriaService.explainTimeTable(timeTableService, timeTableId));
    }
}
