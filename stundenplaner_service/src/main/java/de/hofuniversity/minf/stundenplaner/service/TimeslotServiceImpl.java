package de.hofuniversity.minf.stundenplaner.service;

import de.hofuniversity.minf.stundenplaner.common.NotFoundException;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.timeslot.TimeslotRepository;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.timeslot.data.TimeslotDO;
import de.hofuniversity.minf.stundenplaner.service.boundary.TimeslotService;
import de.hofuniversity.minf.stundenplaner.service.to.TimeslotTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class TimeslotServiceImpl implements TimeslotService {

    private final TimeslotRepository timeslotRepository;

    @Autowired
    public TimeslotServiceImpl(TimeslotRepository timeslotRepository) {
        this.timeslotRepository = timeslotRepository;
    }

    @Override
    public List<TimeslotTO> getAll() {
        return StreamSupport.stream(timeslotRepository.findAll().spliterator(), false)
                .map(TimeslotTO::fromDO)
                .toList();
    }

    @Override
    public TimeslotTO createTimeslot(TimeslotTO timeslotTO) {
        TimeslotDO timeslotDO = TimeslotDO.fromTO(timeslotTO);
        timeslotDO.setId(null);
        return TimeslotTO.fromDO(timeslotRepository.save(timeslotDO));
    }

    @Override
    public TimeslotTO findById(Long id) {
        Optional<TimeslotDO> optional = timeslotRepository.findById(id);
        if (optional.isPresent()){
            return TimeslotTO.fromDO(optional.get());
        } else {
            throw new NotFoundException(TimeslotDO.class, id);
        }
    }

    @Override
    public TimeslotTO updateTimeslot(Long id, TimeslotTO timeslotTO) {
        Optional<TimeslotDO> optional = timeslotRepository.findById(id);
        if (optional.isPresent()){
            TimeslotDO timeslotDO = optional.get();
            timeslotDO.updateFromTO(timeslotTO);
            return TimeslotTO.fromDO(timeslotRepository.save(timeslotDO));
        } else {
            throw new NotFoundException(TimeslotDO.class, id);
        }
    }

    @Override
    public TimeslotTO deleteTimeslot(Long id) {
        Optional<TimeslotDO> optional = timeslotRepository.findById(id);
        if (optional.isPresent()){
            TimeslotDO timeslotDO = optional.get();
            timeslotRepository.delete(timeslotDO);
            return TimeslotTO.fromDO(timeslotDO);
        } else {
            throw new NotFoundException(TimeslotDO.class, id);
        }
    }
}
