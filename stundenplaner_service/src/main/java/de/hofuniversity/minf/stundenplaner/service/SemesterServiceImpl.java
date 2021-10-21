package de.hofuniversity.minf.stundenplaner.service;

import de.hofuniversity.minf.stundenplaner.persistence.program.SemesterRepository;
import de.hofuniversity.minf.stundenplaner.persistence.program.data.SemesterDO;
import de.hofuniversity.minf.stundenplaner.service.boundary.SemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SemesterServiceImpl implements SemesterService {

    private final SemesterRepository semesterRepository;

    @Autowired
    public SemesterServiceImpl(SemesterRepository semesterRepository) {
        this.semesterRepository = semesterRepository;
    }

    @Override
    public List<SemesterDO> findAll() {
        return StreamSupport.stream(semesterRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public List<SemesterDO> findAllByIDs(List<Long> id) {
        return semesterRepository.findAllByIdIn(id);
    }
}
