package de.hofuniversity.minf.stundenplaner.service;

import de.hofuniversity.minf.stundenplaner.persistence.timetable.data.LessonDO;

import java.util.List;

/**
 * @author mheckel
 * Implementation for all business logic regarding the rooms
 */
public interface GenerationService {
    List<LessonDO> generate();
}
