package de.hofuniversity.minf.stundenplaner.rest;

import de.hofuniversity.minf.stundenplaner.common.security.RequiredPermission;
import de.hofuniversity.minf.stundenplaner.persistence.permission.data.PermissionTypeEnum;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.data.LessonDO;
import de.hofuniversity.minf.stundenplaner.service.GenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author mheckel
 * Implementation for all business logic regarding the rooms
 */
@RestController
@RequestMapping("generate")
public class GenerationController {
    private final GenerationService generationService;

    @Autowired
    public GenerationController(GenerationService generationService) {
        this.generationService = generationService;
    }

    @GetMapping
    @RequiredPermission(PermissionTypeEnum.CAN_CREATE_TIME_TABLES)
    public ResponseEntity<List<LessonDO>> generate() {
        return ResponseEntity.ok(generationService.generate());
    }
}
