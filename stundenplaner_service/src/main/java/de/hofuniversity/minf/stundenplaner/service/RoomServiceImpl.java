package de.hofuniversity.minf.stundenplaner.service;

import de.hofuniversity.minf.stundenplaner.common.exception.NotFoundException;
import de.hofuniversity.minf.stundenplaner.persistence.program.data.ProgramDO;
import de.hofuniversity.minf.stundenplaner.persistence.room.RoomRepository;
import de.hofuniversity.minf.stundenplaner.persistence.room.data.RoomDO;
import de.hofuniversity.minf.stundenplaner.service.boundary.RoomService;
import de.hofuniversity.minf.stundenplaner.service.to.RoomTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * @author KMP
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
    public List<RoomTO> getAllRooms() {
        return StreamSupport.stream(roomRepository.findAll().spliterator(), false)
                .map(RoomTO::fromDO)
                .toList();
    }

    @Override
    public RoomTO findById(Long id) {
        Optional<RoomDO> optional = roomRepository.findById(id);
        if (optional.isPresent()) {
            return RoomTO.fromDO(optional.get());
        } else {
            throw new NotFoundException(RoomDO.class, id);
        }
    }


    @Override
    public RoomTO createRoom(RoomTO roomTO) {
        RoomDO roomDO = RoomDO.fromTO(roomTO);
        roomDO.setId(null);
        return RoomTO.fromDO(roomRepository.save(roomDO));
    }

    @Override
    public RoomTO updateRoom(Long id, RoomTO roomTO) {
        Optional<RoomDO> optional = roomRepository.findById(id);
        if (optional.isPresent()) {
            RoomDO roomDO = optional.get();
            roomDO.updateFromTO(roomTO);
            return RoomTO.fromDO(roomRepository.save(roomDO));
        } else {
            throw new NotFoundException(ProgramDO.class, id);
        }
    }

    @Override
    public RoomTO removeRoom(Long id) {
        Optional<RoomDO> optional = roomRepository.findById(id);
        if (optional.isPresent()) {
            RoomDO roomDO = optional.get();
            roomRepository.delete(roomDO);
            return RoomTO.fromDO(roomDO);
        } else {
            throw new NotFoundException(RoomDO.class, id);
        }
    }


}
