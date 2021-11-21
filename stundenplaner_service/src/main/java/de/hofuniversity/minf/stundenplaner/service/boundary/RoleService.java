package de.hofuniversity.minf.stundenplaner.service.boundary;

import de.hofuniversity.minf.stundenplaner.common.NotFoundException;
import de.hofuniversity.minf.stundenplaner.service.to.RoleTO;

import java.util.List;

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
     * @throws NotFoundException if id is not found
     */
    RoleTO findById(Long id);

}
