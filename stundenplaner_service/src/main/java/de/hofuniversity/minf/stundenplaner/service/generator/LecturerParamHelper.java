package de.hofuniversity.minf.stundenplaner.service.generator;

import de.hofuniversity.minf.stundenplaner.persistence.timetable.data.LessonDO;
import de.hofuniversity.minf.stundenplaner.persistence.user.data.UserDO;

/**
 * @author mheckel
 */
public class LecturerParamHelper implements ParamHelper<UserDO> {
    @Override
    public void setParam(LessonDO lesson, UserDO value) {
        lesson.setLecturerDO(value);
    }

    @Override
    public UserDO getParam(LessonDO lesson) {
        return lesson.getLecturerDO();
    }
}
