package de.hofuniversity.minf.stundenplaner.service.boundary;

import de.hofuniversity.minf.stundenplaner.persistence.permission.data.PermissionTypeEnum;
import de.hofuniversity.minf.stundenplaner.persistence.role.data.RoleTypeEnum;
import de.hofuniversity.minf.stundenplaner.service.to.RoleTO;

import java.util.List;
import java.util.Set;

/**
 * Interface for managing roles
 */
public interface RoleService {

    /**
     * Method to get all saved roles
     *
     * @return list of all roles
     */
    List<RoleTO> findAll();

    /**
     * finds a role by its id
     *
     * @param id to check roles for
     * @return role with id
     */
    RoleTO findById(Long id);

    /**
     * finds a role by its type
     *
     * @param type to check roles for
     * @return role with type
     */
    RoleTO findByType(RoleTypeEnum type);

    /**
     * returns the list of permissions within a roleTO
     *
     * @param roleTO of the role to get the permissions from
     * @return Set of permissions within the role
     */
    Set<PermissionTypeEnum> getPermissionsFromRoleTO(RoleTO roleTO);

}
