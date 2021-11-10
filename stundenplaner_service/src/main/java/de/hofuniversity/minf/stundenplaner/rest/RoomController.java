package de.hofuniversity.minf.stundenplaner.rest;

import de.hofuniversity.minf.stundenplaner.service.boundary.RoomService;
import de.hofuniversity.minf.stundenplaner.service.to.RoomTO;
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

/**
 * @author KMP
 * <p>
 * Rest class representing the api endpoints for the room info
 *
 */
@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public List<RoomTO> getAllRooms() {
        return roomService.getAllRooms();
    }

    @PostMapping
    public ResponseEntity<RoomTO> createRoom(@RequestBody RoomTO roomTo){
        return ResponseEntity.status(201).body(roomService.createRoom(roomTo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomTO> findRoomById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(roomService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomTO> updateRoom(@PathVariable("id") Long id, @RequestBody RoomTO roomTO){
        return ResponseEntity.ok(roomService.updateRoom(id, roomTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RoomTO> deleteRoom(@PathVariable("id") Long id){
        return ResponseEntity.ok(roomService.removeRoom(id));
    }

}
