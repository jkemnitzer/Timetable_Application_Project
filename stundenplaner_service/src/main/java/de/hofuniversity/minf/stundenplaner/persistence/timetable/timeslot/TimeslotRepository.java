package de.hofuniversity.minf.stundenplaner.persistence.timetable.timeslot;

import de.hofuniversity.minf.stundenplaner.persistence.timetable.timeslot.data.TimeslotDO;
import org.springframework.data.repository.CrudRepository;

public interface TimeslotRepository extends CrudRepository<TimeslotDO, Long> {}
