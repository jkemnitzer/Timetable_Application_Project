package de.hofuniversity.minf.stundenplaner.service;

import de.hofuniversity.minf.stundenplaner.persistence.data.RoomDO;

import java.util.List;

/**
 * @author nlehmann
 * interface for modularization of service classes
 */
public interface RoomService {

    List<RoomDO> getAllRooms();
    RoomDO findById(Long id);

}
