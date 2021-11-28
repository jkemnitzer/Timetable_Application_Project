package de.hofuniversity.minf.stundenplaner.service;

import de.hofuniversity.minf.stundenplaner.service.to.ModulePreferenceTO;

import java.util.List;

/**
 * @author mheckel
 * interface for modularization of service classes
 */
public interface ModulePreferenceService {

    List<ModulePreferenceTO> getAllModulePreferencesForLecturer(Long lecturerId);
    ModulePreferenceTO findById(Long id);

}
