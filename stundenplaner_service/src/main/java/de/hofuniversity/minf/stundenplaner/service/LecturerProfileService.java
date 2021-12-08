package de.hofuniversity.minf.stundenplaner.service;

import de.hofuniversity.minf.stundenplaner.service.to.LecturerProfileTO;

/**
 * @author mheckel
 * interface for modularization of service classes
 */
public interface LecturerProfileService {

    LecturerProfileTO getLecturerProfileForLecturer(Long lecturerId);
    LecturerProfileTO findById(Long id);

}
