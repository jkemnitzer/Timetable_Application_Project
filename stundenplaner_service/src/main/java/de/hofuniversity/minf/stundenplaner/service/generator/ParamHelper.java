package de.hofuniversity.minf.stundenplaner.service.generator;

import de.hofuniversity.minf.stundenplaner.persistence.timetable.data.LessonDO;

/**
 * @author mheckel
 */
interface ParamHelper<T> {
    void setParam(LessonDO lesson, T value);

    T getParam(LessonDO lesson);
}
