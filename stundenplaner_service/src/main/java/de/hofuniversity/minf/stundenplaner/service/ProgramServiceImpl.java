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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
                .toList();
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
        ProgramDO programDO = ProgramDO.fromTO(programTo);
        programDO.setId(null);
        programDO.getSemesterDOs().forEach(semesterDO -> {
            semesterDO.setProgram(programDO);
            // semester must be new, otherwise it was attached to a program already
            semesterDO.setId(null);
        });
        return ProgramTO.fromDO(programRepository.save(programDO));
    }

    @Override
    public ProgramTO updateProgram(Long id, ProgramTO programTO, boolean checkSemesters) {
        Optional<ProgramDO> optional = programRepository.findById(id);
        if (optional.isPresent()) {
            ProgramDO programDO = optional.get();
            programDO.updateFromTO(programTO);
            if (checkSemesters) {
                mergeSemesters(programDO, programTO.getSemesters());
            }
            return ProgramTO.fromDO(programRepository.save(programDO));
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

    /**
     * finds all semesters by id and program
     * @param program program to find semesters in
     * @param semesterIds ids to look for
     * @return list of semesters in program
     */
    private List<SemesterDO> findSemesterDOsByIds(ProgramDO program, List<Long> semesterIds) {
        return semesterRepository.findAllByProgramAndIdIn(program, semesterIds);
    }

    /**
     * compare and merge two semester lists
     * @param programDO to get old semesters from
     * @param newSemesters list of new semesters
     */
    private void mergeSemesters(ProgramDO programDO, List<SemesterTO> newSemesters) {
        List<Long> identical = new ArrayList<>();
        List<Long> toBeRemoved = new ArrayList<>();
        List<Long> newIds = newSemesters.stream().map(SemesterTO::getId).toList();
        programDO.getSemesterDOs().stream()
                .map(SemesterDO::getId)
                .forEach(id -> {
                    if (newIds.contains(id)) {
                        identical.add(id);
                    } else {
                        toBeRemoved.add(id);
                    }
                });
        removeSemesterFromProgram(programDO, toBeRemoved);
        newSemesters.stream()
                .filter(to -> !identical.contains(to.getId()))
                .map(SemesterDO::fromTO)
                .forEach(semesterDO -> {
                    semesterDO.setProgram(programDO);
                    programDO.getSemesterDOs().add(semesterRepository.save(semesterDO));
                });
    }

    /**
     * removes a semester list from a program and persistence context
     * @param programDO program to remove semesters from
     * @param semesters semesters to be removed
     */
    private void removeSemesterFromProgram(ProgramDO programDO, List<Long> semesters) {
        List<SemesterDO> toBeRemoved = findSemesterDOsByIds(programDO, semesters);
        programDO.getSemesterDOs().removeAll(toBeRemoved);
        semesterRepository.deleteAll(toBeRemoved);
    }
}
