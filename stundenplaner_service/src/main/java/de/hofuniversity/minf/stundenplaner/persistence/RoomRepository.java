package de.hofuniversity.minf.stundenplaner.persistence;

import de.hofuniversity.minf.stundenplaner.persistence.data.RoomDO;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<RoomDO, Long> {}
