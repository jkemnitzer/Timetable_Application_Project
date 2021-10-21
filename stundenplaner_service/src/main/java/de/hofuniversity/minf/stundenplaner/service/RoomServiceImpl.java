package de.hofuniversity.minf.stundenplaner.service;

import de.hofuniversity.minf.stundenplaner.persistence.room.RoomDO;
import de.hofuniversity.minf.stundenplaner.persistence.room.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author nlehmann
 * Implementation for all business logic regarding the rooms
 */
@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<RoomDO> getAllRooms() {
        return StreamSupport
                .stream(roomRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public RoomDO findById(Long id) {
        return roomRepository.findById(id).orElse(null);
    }
}
