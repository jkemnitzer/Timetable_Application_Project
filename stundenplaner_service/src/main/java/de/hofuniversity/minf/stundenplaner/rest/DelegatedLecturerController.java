package de.hofuniversity.minf.stundenplaner.rest;

import de.hofuniversity.minf.stundenplaner.common.security.RequiredPermission;
import de.hofuniversity.minf.stundenplaner.persistence.permission.data.PermissionTypeEnum;
import de.hofuniversity.minf.stundenplaner.service.DelegatedLecturerService;
import de.hofuniversity.minf.stundenplaner.service.to.DelegatedLecturerTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author mheckel
 * <p>
 * Rest class representing the api endpoints for the room info
 */
@RestController
@RequestMapping("/users/{lecturerId}/profile/delegatedLecturers")
public class DelegatedLecturerController {

    private DelegatedLecturerService delegatedLecturerService;

    @Autowired
    public DelegatedLecturerController(DelegatedLecturerService delegatedLecturerService) {
        this.delegatedLecturerService = delegatedLecturerService;
    }

    @GetMapping
    @RequiredPermission(PermissionTypeEnum.CAN_READ_DELEGATED_LECTURERS)
    public ResponseEntity<List<DelegatedLecturerTO>> getAllDelegatedLecturersForLecturer(@PathVariable("lecturerId") Long lecturerId) {
        return ResponseEntity.ok(delegatedLecturerService.getAllDelegatedLecturersForLecturer(lecturerId));
    }

    @GetMapping("/{delegatedLecturerId}")
    @RequiredPermission(PermissionTypeEnum.CAN_READ_DELEGATED_LECTURERS)
    public ResponseEntity<DelegatedLecturerTO> findDelegatedLecturerById(@PathVariable("delegatedLecturerId") Long delegatedLecturerId) {
        return ResponseEntity.ok(delegatedLecturerService.findById(delegatedLecturerId));
    }

}
