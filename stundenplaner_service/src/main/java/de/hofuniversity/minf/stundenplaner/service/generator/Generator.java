package de.hofuniversity.minf.stundenplaner.service.generator;

import de.hofuniversity.minf.stundenplaner.persistence.timetable.data.LessonDO;

import java.util.List;

/**
 * @author mheckel
 */
public interface Generator {
    List<LessonDO> generate();
}
