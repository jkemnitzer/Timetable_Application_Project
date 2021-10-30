package de.hofuniversity.minf.stundenplaner.persistence.lecture;

import org.springframework.data.repository.CrudRepository;

public interface LectureRepository extends CrudRepository<LectureDO, Long> {
}
