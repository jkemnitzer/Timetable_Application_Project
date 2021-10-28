package de.hofuniversity.minf.stundenplaner.service.boundary;

import de.hofuniversity.minf.stundenplaner.common.NotFoundException;
import de.hofuniversity.minf.stundenplaner.service.to.SemesterTO;

import java.util.List;

/**
 * interface to manage semesters
 */
public interface SemesterService {

    /**
     * Gets all semesters for a given program
     * @param programId id of program to get semester for
     * @return all semesters for given program
     */
    List<SemesterTO> findAllByProgramId(Long programId);

    /**
     * creates a semester in a given program
     * @param programId id of program to create semester in
     * @param semesterTO template to create semester from
     * @return created semester
     */
    SemesterTO createSemester(Long programId, SemesterTO semesterTO);

    /**
     * finds semester in program with given id
     * @param programId id of program
     * @param semesterId id of semester to look for
     * @return semester with id
     * @throws NotFoundException if either id is not found
     */
    SemesterTO findById(Long programId, Long semesterId);

    /**
     * updates a semester in a given program
     * @param programId to get semester from
     * @param semesterId semester to update
     * @param semesterTO template to get changes from
     * @return changed semester
     * @throws NotFoundException if either id is not found
     */
    SemesterTO updateSemester(Long programId, Long semesterId, SemesterTO semesterTO);

    /**
     * removes a semester in a given program
     * @param programId program to find semester in
     * @param semesterId semester to change
     * @return removed semester
     * @throws NotFoundException if either id is not found
     */
    SemesterTO removeSemester(Long programId, Long semesterId);
}
