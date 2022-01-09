package de.hofuniversity.minf.stundenplaner.rest;

import de.hofuniversity.minf.stundenplaner.common.security.RequiredPermission;
import de.hofuniversity.minf.stundenplaner.persistence.permission.data.PermissionTypeEnum;
import de.hofuniversity.minf.stundenplaner.service.boundary.RoomService;
import de.hofuniversity.minf.stundenplaner.service.to.RoomTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author KMP
 * <p>
 * Rest class representing the api endpoints for the room info
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
    @RequiredPermission(PermissionTypeEnum.CAN_READ_ROOMS)
    public List<RoomTO> getAllRooms() {
        return roomService.getAllRooms();
    }

    @PostMapping
    @RequiredPermission(PermissionTypeEnum.CAN_CREATE_ROOMS)
    public ResponseEntity<RoomTO> createRoom(@RequestBody RoomTO roomTo) {
        return ResponseEntity.status(201).body(roomService.createRoom(roomTo));
    }

    @GetMapping("/{id}")
    @RequiredPermission(PermissionTypeEnum.CAN_READ_ROOMS)
    public ResponseEntity<RoomTO> findRoomById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(roomService.findById(id));
    }

    @PutMapping("/{id}")
    @RequiredPermission(PermissionTypeEnum.CAN_UPDATE_ROOMS)
    public ResponseEntity<RoomTO> updateRoom(@PathVariable("id") Long id, @RequestBody RoomTO roomTO) {
        return ResponseEntity.ok(roomService.updateRoom(id, roomTO));
    }

    @DeleteMapping("/{id}")
    @RequiredPermission(PermissionTypeEnum.CAN_DELETE_ROOMS)
    public ResponseEntity<RoomTO> deleteRoom(@PathVariable("id") Long id) {
        return ResponseEntity.ok(roomService.removeRoom(id));
    }

}
