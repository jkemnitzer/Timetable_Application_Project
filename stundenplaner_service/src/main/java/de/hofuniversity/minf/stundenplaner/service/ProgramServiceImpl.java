package de.hofuniversity.minf.stundenplaner.service;

import de.hofuniversity.minf.stundenplaner.common.NotFoundException;
import de.hofuniversity.minf.stundenplaner.persistence.program.ProgramRepository;
import de.hofuniversity.minf.stundenplaner.persistence.program.SemesterRepository;
import de.hofuniversity.minf.stundenplaner.persistence.program.data.ProgramDO;
import de.hofuniversity.minf.stundenplaner.persistence.program.data.SemesterDO;
import de.hofuniversity.minf.stundenplaner.service.boundary.ProgramService;
import de.hofuniversity.minf.stundenplaner.service.to.ProgramTO;
import de.hofuniversity.minf.stundenplaner.service.to.SemesterTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProgramServiceImpl implements ProgramService {

    private final ProgramRepository programRepository;
    private final SemesterRepository semesterRepository;

    @Autowired
    public ProgramServiceImpl(ProgramRepository programRepository, SemesterRepository semesterRepository) {
        this.programRepository = programRepository;
        this.semesterRepository = semesterRepository;
    }

    @Override
    public List<ProgramTO> findAll() {
        return StreamSupport.stream(programRepository.findAll().spliterator(), false)
                .map(ProgramTO::fromDO)
                .collect(Collectors.toList());
    }

    @Override
    public ProgramTO findById(Long id) {
        Optional<ProgramDO> optional = programRepository.findById(id);
        if (optional.isPresent()) {
            return ProgramTO.fromDO(optional.get());
        } else {
            throw new NotFoundException(ProgramDO.class, id);
        }
    }

    @Override
    public ProgramTO createProgram(ProgramTO programTo) {
        return ProgramTO.fromDO(
                programRepository.save(ProgramDO.fromTO(programTo))
        );
    }

    @Override
    public ProgramTO updateProgram(Long id, ProgramTO programTO) {
        Optional<ProgramDO> optional = programRepository.findById(id);
        if (optional.isPresent()) {
            ProgramDO programDO = optional.get();
            programDO.updateFromTO(programTO);
            programDO.setSemesterDOs(findSemesterDOsByIds(
                    programTO.getSemesters().stream()
                            .map(SemesterTO::getId)
                            .collect(Collectors.toList())
            ));
            return ProgramTO.fromDO(programDO);
        } else {
            throw new NotFoundException(ProgramDO.class, id);
        }
    }

    @Override
    public ProgramTO removeProgram(Long id) {
        Optional<ProgramDO> optional = programRepository.findById(id);
        if (optional.isPresent()) {
            ProgramDO programDO = optional.get();
            programRepository.delete(programDO);
            return ProgramTO.fromDO(programDO);
        } else {
            throw new NotFoundException(ProgramDO.class, id);
        }
    }

    private List<SemesterDO> findSemesterDOsByIds(List<Long> semesterIds){
        return semesterRepository.findAllByIdIn(semesterIds);
    }
}
