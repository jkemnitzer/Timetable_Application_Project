package de.hofuniversity.minf.stundenplaner.service.boundary;

import de.hofuniversity.minf.stundenplaner.service.to.SemesterTO;

import java.util.List;

public interface SemesterService {

    List<SemesterTO> findAllByProgramId(Long programId);

    SemesterTO createSemester(Long programId, SemesterTO semesterTO);

    SemesterTO findById(Long programId, Long semesterId);

    SemesterTO updateSemester(Long programId, Long semesterId, SemesterTO semesterTO);

    SemesterTO removeSemester(Long programId, Long semesterId);
}
