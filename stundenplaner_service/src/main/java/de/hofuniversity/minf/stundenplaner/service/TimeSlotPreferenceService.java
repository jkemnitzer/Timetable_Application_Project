package de.hofuniversity.minf.stundenplaner.service;

import de.hofuniversity.minf.stundenplaner.service.to.TimeSlotPreferenceTO;

import java.util.List;

/**
 * @author mheckel
 * interface for modularization of service classes
 */
public interface TimeSlotPreferenceService {

    List<TimeSlotPreferenceTO> getAllTimeSlotPreferencesForLecturer(Long lecturerId);
    TimeSlotPreferenceTO findById(Long id);

}
