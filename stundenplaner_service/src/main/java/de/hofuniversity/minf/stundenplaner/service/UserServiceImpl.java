package de.hofuniversity.minf.stundenplaner.service;

import de.hofuniversity.minf.stundenplaner.common.exception.NotFoundException;
import de.hofuniversity.minf.stundenplaner.persistence.role.RoleRepository;
import de.hofuniversity.minf.stundenplaner.persistence.role.data.RoleDO;
import de.hofuniversity.minf.stundenplaner.persistence.user.UserRepository;
import de.hofuniversity.minf.stundenplaner.persistence.user.data.UserDO;
import de.hofuniversity.minf.stundenplaner.service.boundary.UserService;
import de.hofuniversity.minf.stundenplaner.service.to.RoleTO;
import de.hofuniversity.minf.stundenplaner.service.to.UserTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<UserTO> findAll() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(UserTO::fromDO)
                .toList();
    }

    @Override
    public UserTO findById(Long id) {
        Optional<UserDO> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            return UserTO.fromDO(optional.get());
        } else {
            throw new NotFoundException(UserDO.class, id);
        }
    }

    @Override
    public UserTO createUser(UserTO userTO) {
        UserDO userDO = UserDO.fromTO(userTO);
        userDO.setId(null);
        userDO.setCreated(LocalDateTime.now());
        userDO.setLastUpdated(LocalDateTime.now());
        userDO.setPassword("");
        userDO.setPasswordSalt("");

        Set<RoleDO> roles = roleRepository.findAllByIdInOrNameIn(
                userTO.getRoles().stream().map(RoleTO::getId).toList(),
                userTO.getRoles().stream().map(RoleTO::getName).toList());

        userDO.setRoleDOs(roles);

        return UserTO.fromDO(userRepository.save(userDO));
    }

    @Override
    public UserTO updateUser(Long id, UserTO userTO, boolean checkRoles) {
        Optional<UserDO> optionalUserDO = userRepository.findById(id);
        if (optionalUserDO.isPresent()) {
            UserDO userDO = optionalUserDO.get();
            userDO.updateFromTO(userTO);

            if (checkRoles) {
                mergeRoles(userDO, userTO.getRoles());
            }
            return UserTO.fromDO(userRepository.save(userDO));
        } else {
            throw new NotFoundException(UserDO.class, id);
        }
    }

    @Override
    public UserTO removeUser(Long id) {
        Optional<UserDO> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            UserDO userDO = optional.get();
            userRepository.delete(userDO);
            return UserTO.fromDO(userDO);
        } else {
            throw new NotFoundException(UserDO.class, id);
        }
    }

    /**
     * compare and merge two role lists
     *
     * @param userDO   to get old roles from
     * @param newRoles list of new roles
     */
    private void mergeRoles(UserDO userDO, Set<RoleTO> newRoles) {
        Set<Long> identical = new HashSet<>();
        Set<Long> toBeRemoved = new HashSet<>();
        Set<Long> newIds = newRoles.stream().map(RoleTO::getId).collect(Collectors.toSet());
        userDO.getRoleDOs().stream()
                .map(RoleDO::getId)
                .forEach(id -> {
                    if (newIds.contains(id)) {
                        identical.add(id);
                    } else {
                        toBeRemoved.add(id);
                    }
                });
        removeRolesFromUser(userDO, toBeRemoved);
        newRoles.stream()
                .filter(to -> !identical.contains(to.getId()))
                .map(RoleDO::fromTO)
                .forEach(roleDO -> userDO.getRoleDOs().add(roleRepository.save(roleDO)));
    }

    /**
     * removes a role list from a user
     *
     * @param userDO user to remove roles from
     * @param roles  roles to be removed
     */
    private void removeRolesFromUser(UserDO userDO, Set<Long> roles) {
        Set<RoleDO> toBeRemoved = findRoleDOsByIds(roles);
        toBeRemoved.forEach(userDO.getRoleDOs()::remove);
    }

    /**
     * finds all roles by id
     *
     * @param roleIds ids to look for
     * @return list of roles in user
     */
    private Set<RoleDO> findRoleDOsByIds(Set<Long> roleIds) {
        return StreamSupport.stream(roleRepository.findAllById(roleIds).spliterator(), false).collect(Collectors.toSet());
    }
}
