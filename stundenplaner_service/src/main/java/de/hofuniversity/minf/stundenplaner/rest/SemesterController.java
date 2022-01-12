package de.hofuniversity.minf.stundenplaner.rest;

import de.hofuniversity.minf.stundenplaner.common.security.RequiredPermission;
import de.hofuniversity.minf.stundenplaner.persistence.permission.data.PermissionTypeEnum;
import de.hofuniversity.minf.stundenplaner.service.boundary.SemesterService;
import de.hofuniversity.minf.stundenplaner.service.to.SemesterTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("programs/{programId}/semester")
public class SemesterController {

    private final SemesterService semesterService;

    @Autowired
    public SemesterController(SemesterService semesterService) {
        this.semesterService = semesterService;
    }

    @GetMapping
    @RequiredPermission(PermissionTypeEnum.CAN_READ_SEMESTERS)
    public ResponseEntity<List<SemesterTO>> getAllSemesters(@PathVariable("programId") Long programId) {
        return ResponseEntity.ok(semesterService.findAllByProgramId(programId));
    }

    @PostMapping
    @RequiredPermission(PermissionTypeEnum.CAN_CREATE_SEMESTERS)
    public ResponseEntity<SemesterTO> createSemester(
            @PathVariable("programId") Long programId,
            @RequestBody SemesterTO semesterTO) {
        return ResponseEntity.status(201).body(semesterService.createSemester(programId, semesterTO));
    }

    @GetMapping("/{semesterId}")
    @RequiredPermission(PermissionTypeEnum.CAN_READ_SEMESTERS)
    public ResponseEntity<SemesterTO> findById(
            @PathVariable("programId") Long programId,
            @PathVariable("semesterId") Long semesterId) {
        return ResponseEntity.ok(semesterService.findById(programId, semesterId));
    }

    @PutMapping("/{semesterId}")
    @RequiredPermission(PermissionTypeEnum.CAN_UPDATE_SEMESTERS)
    public ResponseEntity<SemesterTO> updateSemester(
            @PathVariable("programId") Long programId,
            @PathVariable("semesterId") Long semesterId,
            @RequestBody SemesterTO semesterTO) {
        return ResponseEntity.ok(semesterService.updateSemester(programId, semesterId, semesterTO));
    }

    @DeleteMapping("/{semesterId}")
    @RequiredPermission(PermissionTypeEnum.CAN_DELETE_SEMESTERS)
    public ResponseEntity<SemesterTO> deleteSemester(
            @PathVariable("programId") Long programId,
            @PathVariable("semesterId") Long semesterId) {
        return ResponseEntity.ok(semesterService.removeSemester(programId, semesterId));
    }

}
