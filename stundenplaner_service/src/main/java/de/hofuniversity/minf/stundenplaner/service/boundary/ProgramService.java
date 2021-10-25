package de.hofuniversity.minf.stundenplaner.service.boundary;

import de.hofuniversity.minf.stundenplaner.common.NotFoundException;
import de.hofuniversity.minf.stundenplaner.service.to.ProgramTO;

import java.util.List;

/**
 * Interface for managing programs
 */
public interface ProgramService {

    /**
     * Method to get all saved programs
     * @return list of all programs
     */
    List<ProgramTO> findAll();

    /**
     * finds a program by its id
     * @param id to check programs for
     * @return program with id
     * @throws NotFoundException if id is not found
     */
    ProgramTO findById(Long id);

    /**
     * creates a program by given template
     * @param programTo template to create program from
     * @return program as saved in database layer
     */
    ProgramTO createProgram(ProgramTO programTo);

    /**
     * updates a given program by the given template
     * @param id program to update
     * @param programTO template to get changes from
     * @param checkSemesters update semesters as well or only basic attributes
     * @return updated program as saved in database layer
     * @throws NotFoundException if id is not found
     */
    ProgramTO updateProgram(Long id, ProgramTO programTO, boolean checkSemesters);

    /**
     * removes a given program from the database layer
     * @param id program to be removed
     * @return instance of program that was removed
     * @throws NotFoundException if id is not found
     */
    ProgramTO removeProgram(Long id);

}
