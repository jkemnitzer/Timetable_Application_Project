package de.hofuniversity.minf.stundenplaner.service;

import de.hofuniversity.minf.stundenplaner.common.exception.NotFoundException;
import de.hofuniversity.minf.stundenplaner.persistence.permission.data.PermissionTypeEnum;
import de.hofuniversity.minf.stundenplaner.persistence.role.RoleRepository;
import de.hofuniversity.minf.stundenplaner.persistence.role.data.RoleDO;
import de.hofuniversity.minf.stundenplaner.persistence.role.data.RoleTypeEnum;
import de.hofuniversity.minf.stundenplaner.service.boundary.RoleService;
import de.hofuniversity.minf.stundenplaner.service.to.PermissionTO;
import de.hofuniversity.minf.stundenplaner.service.to.RoleTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.StreamSupport;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleTO> findAll() {
        return StreamSupport.stream(roleRepository.findAll().spliterator(), false)
                .map(RoleTO::fromDO)
                .toList();
    }

    @Override
    public RoleTO findById(Long id) {
        Optional<RoleDO> optional = roleRepository.findById(id);
        if (optional.isPresent()) {
            return RoleTO.fromDO(optional.get());
        } else {
            throw new NotFoundException(RoleDO.class, id);
        }
    }

    @Override
    public RoleTO findByType(RoleTypeEnum type) {
        Optional<RoleDO> optional = roleRepository.findByType(type);
        if (optional.isPresent()) {
            return RoleTO.fromDO(optional.get());
        } else {
            throw new NotFoundException(RoleDO.class, null);
        }
    }

    /**
     * returns the list of permissions within a roleTO
     *
     * @param roleTO of the role to get the permissions from
     * @return Set of permissions within the role
     */
    public Set<PermissionTypeEnum> getPermissionsFromRoleTO(RoleTO roleTO) {

        Set<PermissionTypeEnum> permissions = new HashSet<>();
        for (PermissionTO permissionTO : roleTO.getPermissions()) {
            permissions.add(PermissionTypeEnum.valueOf(permissionTO.getType()));
        }

        return permissions;
    }
}
