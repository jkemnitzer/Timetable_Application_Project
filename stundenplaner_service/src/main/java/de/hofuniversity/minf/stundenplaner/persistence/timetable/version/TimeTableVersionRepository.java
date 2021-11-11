package de.hofuniversity.minf.stundenplaner.persistence.timetable.version;

import org.springframework.data.repository.CrudRepository;

public interface TimeTableVersionRepository extends CrudRepository<TimeTableVersionDO, Long> {
}
