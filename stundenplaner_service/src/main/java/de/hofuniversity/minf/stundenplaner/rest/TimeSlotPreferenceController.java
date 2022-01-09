package de.hofuniversity.minf.stundenplaner.rest;

import de.hofuniversity.minf.stundenplaner.common.security.RequiredPermission;
import de.hofuniversity.minf.stundenplaner.persistence.permission.data.PermissionTypeEnum;
import de.hofuniversity.minf.stundenplaner.service.TimeSlotPreferenceService;
import de.hofuniversity.minf.stundenplaner.service.to.TimeSlotPreferenceTO;
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
@RequestMapping("/users/{lecturerId}/profile/timeSlotPreferences")
public class TimeSlotPreferenceController {

    private TimeSlotPreferenceService timeSlotPreferenceService;

    @Autowired
    public TimeSlotPreferenceController(TimeSlotPreferenceService timeSlotPreferenceService) {
        this.timeSlotPreferenceService = timeSlotPreferenceService;
    }

    @GetMapping
    @RequiredPermission(PermissionTypeEnum.CAN_READ_TIME_SLOT_PREFERENCES)
    public ResponseEntity<List<TimeSlotPreferenceTO>> get(@PathVariable("lecturerId") Long lecturerId) {
        return ResponseEntity.ok(timeSlotPreferenceService.getAllTimeSlotPreferencesForLecturer(lecturerId));
    }

    @GetMapping("/{timeSlotPreferenceId}")
    @RequiredPermission(PermissionTypeEnum.CAN_READ_TIME_SLOT_PREFERENCES)
    public ResponseEntity<TimeSlotPreferenceTO> findDelegatedLecturerById(@PathVariable("timeSlotPreferenceId") Long timeSlotPreferenceId) {
        return ResponseEntity.ok(timeSlotPreferenceService.findById(timeSlotPreferenceId));
    }

}
