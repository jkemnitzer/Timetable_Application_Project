package de.hofuniversity.minf.stundenplaner.persistence.timetable;

import de.hofuniversity.minf.stundenplaner.persistence.lecture.data.LectureDO;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.data.LessonDO;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.version.TimeTableVersionDO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TimeTableRepository extends CrudRepository<LessonDO, Long>, JpaSpecificationExecutor<LessonDO> {

    List<LessonDO> findAllByTimeTableVersionDO(TimeTableVersionDO timeTableVersionDO);

    List<LessonDO> findAllByLectureDOAndIdIn(LectureDO lectureDO, List<Long> ids);

    void deleteAllByTimeTableVersionDO(TimeTableVersionDO timeTableVersionDO);

}