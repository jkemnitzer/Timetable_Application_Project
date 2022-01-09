package de.hofuniversity.minf.stundenplaner.service.boundary;

import de.hofuniversity.minf.stundenplaner.persistence.permission.data.PermissionTypeEnum;
import de.hofuniversity.minf.stundenplaner.persistence.user.data.UserDO;
import de.hofuniversity.minf.stundenplaner.service.to.UserTO;

import java.util.List;
import java.util.Set;

/**
 * Interface for managing users
 */
public interface UserService {

    /**
     * Method to get all saved users
     *
     * @return list of all users
     */
    List<UserTO> findAll();

    /**
     * finds a user by its id
     *
     * @param id to check users for
     * @return user with id
     */
    UserTO findById(Long id);

    /**
     * creates a user by given template
     *
     * @param userTO template to create user from
     * @return user as saved in database layer
     */
    UserTO createUser(UserTO userTO);

    /**
     * updates a given user by the given template
     *
     * @param id         user to update
     * @param userTO     template to get changes from
     * @param checkRoles update roles as well or only basic attributes
     * @return updated user as saved in database layer
     */
    UserTO updateUser(Long id, UserTO userTO, boolean checkRoles);

    /**
     * removes a given user from the database layer
     *
     * @param id of user to be removed
     * @return instance of user that was removed
     */
    UserTO removeUser(Long id);

    /**
     * returns the list of permissions within a UserDO
     *
     * @param userDO of the User to get the permissions from
     * @return Set of permissions within the User
     */

    Set<PermissionTypeEnum> getPermissionsFromUserDO(UserDO userDO);

}
