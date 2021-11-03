package de.hofuniversity.minf.stundenplaner.persistence.room;

import de.hofuniversity.minf.stundenplaner.persistence.room.data.RoomDO;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<RoomDO, Long> {
}
