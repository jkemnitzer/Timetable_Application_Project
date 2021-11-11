package de.hofuniversity.minf.stundenplaner.service;

import de.hofuniversity.minf.stundenplaner.common.NotFoundException;
import de.hofuniversity.minf.stundenplaner.persistence.faculty.FacultyRepository;
import de.hofuniversity.minf.stundenplaner.persistence.faculty.data.FacultyDO;
import de.hofuniversity.minf.stundenplaner.service.boundary.FacultyService;
import de.hofuniversity.minf.stundenplaner.service.to.FacultyTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * @author Jonas Kemnitzer
 */

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;

    @Autowired
    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public List<FacultyTO> getAllFaculties() {
        return StreamSupport.stream(facultyRepository.findAll().spliterator(), false).map(FacultyTO::fromDO)
                .toList();
    }

    @Override
    public FacultyTO findById(Long id) {
        Optional<FacultyDO> optional = facultyRepository.findById(id);
        if (optional.isPresent()) {
            return FacultyTO.fromDO(optional.get());
        } else {
            throw new NotFoundException(FacultyDO.class, id);
        }
    }

}
