package de.hofuniversity.minf.stundenplaner.persistence.program;

import de.hofuniversity.minf.stundenplaner.persistence.program.data.SemesterDO;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SemesterRepository extends CrudRepository<SemesterDO, Long> {

    List<SemesterDO> findAllByIdIn(List<Long> ids);

}
