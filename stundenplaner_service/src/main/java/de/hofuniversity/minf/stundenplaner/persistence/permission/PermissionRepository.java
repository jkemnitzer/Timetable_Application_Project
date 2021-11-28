package de.hofuniversity.minf.stundenplaner.persistence.permission;

import de.hofuniversity.minf.stundenplaner.persistence.permission.data.PermissionDO;
import de.hofuniversity.minf.stundenplaner.persistence.permission.data.PermissionTypeEnum;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Set;

public interface PermissionRepository extends CrudRepository<PermissionDO, Long> {

    Set<PermissionDO> findAllByIdInOrTypeIn(Collection<Long> id, Collection<PermissionTypeEnum> type);
}
