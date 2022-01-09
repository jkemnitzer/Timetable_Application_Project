package de.hofuniversity.minf.stundenplaner.rest;

import de.hofuniversity.minf.stundenplaner.common.security.RequiredPermission;
import de.hofuniversity.minf.stundenplaner.persistence.permission.data.PermissionTypeEnum;
import de.hofuniversity.minf.stundenplaner.service.ModulePreferenceService;
import de.hofuniversity.minf.stundenplaner.service.to.ModulePreferenceTO;
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
@RequestMapping("/users/{lecturerId}/profile/modulePreferences")
public class ModulePreferenceController {

    private ModulePreferenceService modulePreferenceService;

    @Autowired
    public ModulePreferenceController(ModulePreferenceService modulePreferenceService) {
        this.modulePreferenceService = modulePreferenceService;
    }

    @GetMapping
    @RequiredPermission(PermissionTypeEnum.CAN_READ_MODULE_PREFERENCES)
    public ResponseEntity<List<ModulePreferenceTO>> get(@PathVariable("lecturerId") Long lecturerId) {
        return ResponseEntity.ok(modulePreferenceService.getAllModulePreferencesForLecturer(lecturerId));
    }

    @GetMapping("/{modulePreferenceId}")
    @RequiredPermission(PermissionTypeEnum.CAN_READ_MODULE_PREFERENCES)
    public ResponseEntity<ModulePreferenceTO> findDelegatedLecturerById(@PathVariable("modulePreferenceId") Long modulePreferenceId) {
        return ResponseEntity.ok(modulePreferenceService.findById(modulePreferenceId));
    }

}
