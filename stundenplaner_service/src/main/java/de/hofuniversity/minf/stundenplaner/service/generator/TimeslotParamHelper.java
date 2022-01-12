package de.hofuniversity.minf.stundenplaner.service.generator;

import de.hofuniversity.minf.stundenplaner.persistence.timetable.data.LessonDO;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.timeslot.data.TimeslotDO;

/**
 * @author mheckel
 */
public class TimeslotParamHelper implements ParamHelper<TimeslotDO> {
    @Override
    public void setParam(LessonDO lesson, TimeslotDO timeslot) {
        lesson.setTimeslotDO(timeslot);
    }

    @Override
    public TimeslotDO getParam(LessonDO lesson) {
        return lesson.getTimeslotDO();
    }
}
