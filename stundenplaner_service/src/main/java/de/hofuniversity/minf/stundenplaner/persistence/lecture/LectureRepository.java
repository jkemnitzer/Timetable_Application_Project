package de.hofuniversity.minf.stundenplaner.persistence.lecture;

import de.hofuniversity.minf.stundenplaner.persistence.lecture.data.LectureDO;
import org.springframework.data.repository.CrudRepository;

public interface LectureRepository extends CrudRepository<LectureDO, Long> {
}
