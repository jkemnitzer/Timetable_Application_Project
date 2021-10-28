package de.hofuniversity.minf.stundenplaner.persistence.program;

import de.hofuniversity.minf.stundenplaner.persistence.program.data.ProgramDO;
import org.springframework.data.repository.CrudRepository;

public interface ProgramRepository extends CrudRepository<ProgramDO, Long> {
}
