package de.hofuniversity.minf.stundenplaner.service.boundary;

import de.hofuniversity.minf.stundenplaner.service.to.FacultyTO;

import java.util.List;

/**
 * @author Jonas Kemnitzer interface for modularization of service classes
 */
public interface FacultyService {

    List<FacultyTO> getAllFaculties();

    FacultyTO findById(Long id);

}
