package de.hofuniversity.minf.stundenplaner.persistence.lecture;


import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LessonRepository extends CrudRepository<LessonDO, Long> {
    List<LessonDO> findAllByLectureAndIdIn(LectureDO lecture, List<Long> ids);
}
