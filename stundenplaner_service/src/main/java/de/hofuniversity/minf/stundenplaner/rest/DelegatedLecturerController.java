package de.hofuniversity.minf.stundenplaner.rest;

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
 *
 * Rest class representing the api endpoints for the room info
 */
@RestController
@RequestMapping("/lecturers/{lecturerId}/delegatedLecturers")
public class DelegatedLecturerController {

    private DelegatedLecturerService delegatedLecturerService;

    @Autowired
    public DelegatedLecturerController(DelegatedLecturerService delegatedLecturerService) {
        this.delegatedLecturerService = delegatedLecturerService;
    }

    @GetMapping
    public ResponseEntity<List<DelegatedLecturerTO>> getAllDelegatedLecturersForLecturer(@PathVariable("lecturerId") Long lecturerId){
        return ResponseEntity.ok(delegatedLecturerService.getAllDelegatedLecturersForLecturer(lecturerId));
    }

    @GetMapping("/{delegatedLecturerId}")
    public ResponseEntity<DelegatedLecturerTO> findDelegatedLecturerById(@PathVariable("delegatedLecturerId") Long delegatedLecturerId){
        return ResponseEntity.ok(delegatedLecturerService.findById(delegatedLecturerId));
    }

}
