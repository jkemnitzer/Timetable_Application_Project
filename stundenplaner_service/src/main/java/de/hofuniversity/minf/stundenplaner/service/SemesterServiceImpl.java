package de.hofuniversity.minf.stundenplaner.service;

import de.hofuniversity.minf.stundenplaner.common.exception.NotFoundException;
import de.hofuniversity.minf.stundenplaner.persistence.program.ProgramRepository;
import de.hofuniversity.minf.stundenplaner.persistence.program.SemesterRepository;
import de.hofuniversity.minf.stundenplaner.persistence.program.data.ProgramDO;
import de.hofuniversity.minf.stundenplaner.persistence.program.data.SemesterDO;
import de.hofuniversity.minf.stundenplaner.service.boundary.SemesterService;
import de.hofuniversity.minf.stundenplaner.service.to.SemesterTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SemesterServiceImpl implements SemesterService {

    private final SemesterRepository semesterRepository;
    private final ProgramRepository programRepository;

    @Autowired
    public SemesterServiceImpl(SemesterRepository semesterRepository, ProgramRepository programRepository) {
        this.semesterRepository = semesterRepository;
        this.programRepository = programRepository;
    }

    @Override
    public List<SemesterTO> findAllByProgramId(Long programId) {
        ProgramDO programDO = findProgramDOById(programId);
        return semesterRepository.findAllByProgram(programDO).stream()
                .map(SemesterTO::fromDO)
                .toList();
    }

    @Override
    public SemesterTO createSemester(Long programId, SemesterTO semesterTO) {
        ProgramDO programDO = findProgramDOById(programId);
        SemesterDO semesterDO = SemesterDO.fromTO(semesterTO);
        semesterDO.setProgram(programDO);
        semesterRepository.save(semesterDO);
        programDO.getSemesterDOs().add(semesterDO);
        return SemesterTO.fromDO(semesterDO);
    }

    @Override
    public SemesterTO findById(Long programId, Long semesterId) {
        Optional<SemesterDO> optional = findProgramDOById(programId).getSemesterDOs().stream()
                .filter(semesterDO -> semesterId.equals(semesterDO.getId()))
                .findFirst();
        if (optional.isPresent()) {
            return SemesterTO.fromDO(optional.get());
        } else {
            throw new NotFoundException(SemesterDO.class, semesterId);
        }
    }

    @Override
    public SemesterTO updateSemester(Long programId, Long semesterId, SemesterTO semesterTO) {
        SemesterDO semesterDO = findSemesterDOById(programId, semesterId);
        semesterDO.updateFromTO(semesterTO);
        return SemesterTO.fromDO(semesterDO);
    }

    @Override
    public SemesterTO removeSemester(Long programId, Long semesterId) {
        SemesterDO semesterDO = findSemesterDOById(programId, semesterId);
        ProgramDO programDO = findProgramDOById(programId);
        programDO.getSemesterDOs().remove(semesterDO);
        semesterRepository.delete(semesterDO);
        return SemesterTO.fromDO(semesterDO);
    }

    /**
     * finds a semester DO by its id and program
     * @param programId program to look for
     * @param semesterId semester to find
     * @return semesterDO
     * @throws NotFoundException id either id is not found
     */
    private SemesterDO findSemesterDOById(Long programId, Long semesterId) {
        ProgramDO programDO = findProgramDOById(programId);
        Optional<SemesterDO> optional = programDO.getSemesterDOs().stream()
                .filter(semesterDO -> semesterId.equals(semesterDO.getId()))
                .findFirst();
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new NotFoundException(SemesterDO.class, semesterId);
        }
    }

    /**
     * finds a program DO by its id
     * @param programId program to find
     * @return program DO
     * @throws NotFoundException if program id not found
     */
    private ProgramDO findProgramDOById(Long programId) {
        Optional<ProgramDO> optional = programRepository.findById(programId);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new NotFoundException(ProgramDO.class, programId);
        }
    }
}
