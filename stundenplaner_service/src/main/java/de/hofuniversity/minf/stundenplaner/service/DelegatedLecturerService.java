package de.hofuniversity.minf.stundenplaner.service;

import de.hofuniversity.minf.stundenplaner.service.to.DelegatedLecturerTO;

import java.util.List;

/**
 * @author mheckel
 * interface for modularization of service classes
 */
public interface DelegatedLecturerService {

    List<DelegatedLecturerTO> getAllDelegatedLecturersForLecturer(Long lecturerId);
    DelegatedLecturerTO findById(Long id);

}
