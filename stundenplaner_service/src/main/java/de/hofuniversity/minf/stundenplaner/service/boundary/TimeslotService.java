package de.hofuniversity.minf.stundenplaner.service.boundary;

import de.hofuniversity.minf.stundenplaner.service.to.TimeslotTO;

import java.util.List;

public interface TimeslotService {

    List<TimeslotTO> getAll();

    TimeslotTO createTimeslot(TimeslotTO timeslotTO);

    TimeslotTO findById(Long id);

    TimeslotTO updateTimeslot(Long id, TimeslotTO timeslotTO);

    TimeslotTO deleteTimeslot(Long id);

}
