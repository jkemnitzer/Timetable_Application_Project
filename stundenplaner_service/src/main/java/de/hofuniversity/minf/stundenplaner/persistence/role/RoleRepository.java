package de.hofuniversity.minf.stundenplaner.persistence.role;

import de.hofuniversity.minf.stundenplaner.persistence.role.data.RoleDO;
import de.hofuniversity.minf.stundenplaner.persistence.role.data.RoleTypeEnum;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Set;

public interface RoleRepository extends CrudRepository<RoleDO, Long> {

    Set<RoleDO> findAllByIdInOrTypeIn(Collection<Long> id, Collection<RoleTypeEnum> type);
}