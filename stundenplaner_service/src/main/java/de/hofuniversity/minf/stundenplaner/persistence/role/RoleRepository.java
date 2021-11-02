package de.hofuniversity.minf.stundenplaner.persistence.role;

import de.hofuniversity.minf.stundenplaner.persistence.role.data.RoleDO;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface RoleRepository extends CrudRepository<RoleDO, Long> {

    Set<RoleDO> findAllByIdInOrNameIn(List<Long> id, List<String> name);

}