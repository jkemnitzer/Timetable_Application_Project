package de.hofuniversity.minf.stundenplaner.persistence.faculty;

import de.hofuniversity.minf.stundenplaner.persistence.faculty.data.FacultyDO;
import org.springframework.data.repository.CrudRepository;

public interface FacultyRepository extends CrudRepository<FacultyDO, Long> {

}
