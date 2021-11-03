package de.hofuniversity.minf.stundenplaner.service.boundary;

import de.hofuniversity.minf.stundenplaner.service.to.RoomTO;

import java.util.List;

/**
 * @author KMP
 * interface for modularization of service classes
 */
public interface RoomService {

    List<RoomTO> getAllRooms();

    RoomTO findById(Long id);

    RoomTO createRoom(RoomTO roomTo);

    RoomTO updateRoom(Long id, RoomTO roomTO);

    RoomTO removeRoom(Long id);


}
