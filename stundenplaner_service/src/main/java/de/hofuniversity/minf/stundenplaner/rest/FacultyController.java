package de.hofuniversity.minf.stundenplaner.rest;

import de.hofuniversity.minf.stundenplaner.common.security.RequiredPermission;
import de.hofuniversity.minf.stundenplaner.persistence.permission.data.PermissionTypeEnum;
import de.hofuniversity.minf.stundenplaner.service.boundary.FacultyService;
import de.hofuniversity.minf.stundenplaner.service.to.FacultyTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/faculties")
public class FacultyController {

    private final FacultyService facultyService;

    @Autowired
    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping
    @RequiredPermission(PermissionTypeEnum.CAN_READ_FACULTIES)
    public List<FacultyTO> getAllFaculties() {
        return facultyService.getAllFaculties();
    }

    @GetMapping("/{id}")
    @RequiredPermission(PermissionTypeEnum.CAN_READ_FACULTIES)
    public ResponseEntity<FacultyTO> findFacultyById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(facultyService.findById(id));
    }

}
