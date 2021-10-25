package de.hofuniversity.minf.stundenplaner.service.boundary;

import de.hofuniversity.minf.stundenplaner.service.to.ProgramTO;

import java.util.List;

public interface ProgramService {

    List<ProgramTO> findAll();

    ProgramTO findById(Long id);

    ProgramTO createProgram(ProgramTO programTo);

    ProgramTO updateProgram(Long id, ProgramTO programTO, boolean checkSemesters);

    ProgramTO removeProgram(Long id);

}
