package de.hofuniversity.minf.stundenplaner.service.generator;

import de.hofuniversity.minf.stundenplaner.persistence.room.data.RoomDO;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.data.LessonDO;

/**
 * @author mheckel
 */
public class RoomParamHelper implements ParamHelper<RoomDO>{
    @Override
    public void setParam(LessonDO lesson, RoomDO room) {
        lesson.setRoomDO(room);
    }

    @Override
    public RoomDO getParam(LessonDO lesson) {
        return lesson.getRoomDO();
    }
}
