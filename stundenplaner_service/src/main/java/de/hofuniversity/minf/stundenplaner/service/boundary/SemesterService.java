package de.hofuniversity.minf.stundenplaner.service.boundary;

import de.hofuniversity.minf.stundenplaner.persistence.program.data.SemesterDO;

import java.util.List;

public interface SemesterService {

    List<SemesterDO> findAll();

    List<SemesterDO> findAllByIDs(List<Long> id);

}
