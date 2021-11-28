package de.hofuniversity.minf.stundenplaner.rest;

import de.hofuniversity.minf.stundenplaner.service.boundary.TimeslotService;
import de.hofuniversity.minf.stundenplaner.service.to.TimeslotTO;
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
@RequestMapping("/timeSlots")
public class TimeSlotController {

    private TimeslotService timeSlotService;

    @Autowired
    public TimeSlotController(TimeslotService timeSlotService) {
        this.timeSlotService = timeSlotService;
    }

    @GetMapping
    public ResponseEntity<List<TimeslotTO>> get(){
        return ResponseEntity.ok(timeSlotService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeslotTO> findDelegatedLecturerById(@PathVariable Long id){
        return ResponseEntity.ok(timeSlotService.findById(id));
    }

}
