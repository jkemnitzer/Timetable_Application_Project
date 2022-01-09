package de.hofuniversity.minf.stundenplaner.rest;

import de.hofuniversity.minf.stundenplaner.common.security.RequiredPermission;
import de.hofuniversity.minf.stundenplaner.persistence.permission.data.PermissionTypeEnum;
import de.hofuniversity.minf.stundenplaner.service.LecturerProfileService;
import de.hofuniversity.minf.stundenplaner.service.to.LecturerProfileTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mheckel
 * <p>
 * Rest class representing the api endpoints for the room info
 */
@RestController
@RequestMapping("/users/{lecturerId}/profile")
public class LecturerProfileController {

    private LecturerProfileService lecturerProfileService;

    @Autowired
    public LecturerProfileController(LecturerProfileService lecturerProfileService) {
        this.lecturerProfileService = lecturerProfileService;
    }

    @GetMapping
    @RequiredPermission(PermissionTypeEnum.CAN_READ_LECTURER_PROFILES)
    public ResponseEntity<LecturerProfileTO> get(@PathVariable("lecturerId") Long lecturerId) {
        return ResponseEntity.ok(lecturerProfileService.getLecturerProfileForLecturer(lecturerId));
    }

    @GetMapping("/{lecturerProfileId}")
    @RequiredPermission(PermissionTypeEnum.CAN_READ_LECTURER_PROFILES)
    public ResponseEntity<LecturerProfileTO> findLecturerProfileById(@PathVariable("lecturerProfileId") Long lecturerProfileId) {
        return ResponseEntity.ok(lecturerProfileService.findById(lecturerProfileId));
    }

}
