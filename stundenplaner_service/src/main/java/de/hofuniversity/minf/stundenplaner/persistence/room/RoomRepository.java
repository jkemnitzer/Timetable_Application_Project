package de.hofuniversity.minf.stundenplaner.persistence.room;

import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<RoomDO, Long> {
}
