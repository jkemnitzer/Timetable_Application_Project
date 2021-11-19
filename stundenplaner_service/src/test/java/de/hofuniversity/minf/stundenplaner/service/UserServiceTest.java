package de.hofuniversity.minf.stundenplaner.service;

import de.hofuniversity.minf.stundenplaner.common.exception.NotFoundException;
import de.hofuniversity.minf.stundenplaner.persistence.role.RoleRepository;
import de.hofuniversity.minf.stundenplaner.persistence.user.UserRepository;
import de.hofuniversity.minf.stundenplaner.persistence.user.data.StatusEnum;
import de.hofuniversity.minf.stundenplaner.persistence.user.data.UserDO;
import de.hofuniversity.minf.stundenplaner.service.to.UserTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    private final UserDO USER_DO_1 = new UserDO(1L, "BJupiter", "title", "first", "last", "", "",
            "jb@hof-university.de", LocalDateTime.now(), LocalDateTime.now(), StatusEnum.ACTIVE, Collections.emptySet());
    private final UserDO USER_DO_2 = new UserDO(2L, "CMars", "title", "first", "last","", "",
            "cm@hof-university.de", LocalDateTime.now(), LocalDateTime.now(), StatusEnum.ACTIVE, Collections.emptySet());

    @Test
    void testGetAll() {
        when(userRepository.findAll()).thenReturn(List.of(USER_DO_1, USER_DO_2));
        List<UserDO> expected = List.of(USER_DO_1, USER_DO_2);
        List<UserTO> userTOs = userService.findAll();

        Assertions.assertEquals(expected.size(), userTOs.size());
        Assertions.assertArrayEquals(
                expected.stream().map(UserDO::getUsername).toArray(),
                userTOs.stream().map(UserTO::getUsername).toArray()
        );
    }

    @Test
    void testGetById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(USER_DO_1));
        when(userRepository.findById(3L)).thenReturn(Optional.empty());

        Assertions.assertEquals(USER_DO_1.getId(), userService.findById(1L).getId());
        Assertions.assertThrows(NotFoundException.class, () -> userService.findById(3L));
    }

    @Test
    void testCreate() {
        when(userRepository.save(any(UserDO.class))).thenReturn(USER_DO_1);
        when(roleRepository.findAllByIdInOrNameIn(anyList(), anyList())).thenReturn(Collections.emptySet());
        UserTO testTO = UserTO.fromDO(USER_DO_1);
        testTO.setId(null);

        UserTO actual = userService.createUser(testTO);
        Assertions.assertEquals(USER_DO_1.getId(), actual.getId());
        Assertions.assertEquals(USER_DO_1.getUsername(), actual.getUsername());
    }

    @Test
    void testUpdate() {
        UserDO testReturn = new UserDO(USER_DO_1.getId(), USER_DO_1.getUsername(), "title", "first", "last","", "", USER_DO_1.getEmail(),
                USER_DO_1.getCreated(), USER_DO_1.getLastUpdated(), StatusEnum.ACTIVE, Collections.emptySet());
        when(userRepository.findById(1L)).thenReturn(Optional.of(testReturn), Optional.empty());
        when(userRepository.save(any(UserDO.class))).thenReturn(testReturn);
        UserTO test = UserTO.fromDO(USER_DO_2);

        UserTO actual = userService.updateUser(1L, test, false);
        Assertions.assertNotEquals(USER_DO_1.getUsername(), actual.getUsername());
        Assertions.assertEquals(USER_DO_2.getUsername(), actual.getUsername());

        Assertions.assertThrows(
                NotFoundException.class,
                () -> userService.updateUser(3L, test, false)
        );
    }

    @Test
    void testDelete() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(USER_DO_1), Optional.empty());

        Assertions.assertEquals(USER_DO_1.getId(), userService.removeUser(1L).getId());
        Assertions.assertThrows(NotFoundException.class, () -> userService.removeUser(1L));
    }

}
