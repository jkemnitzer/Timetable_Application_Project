package de.hofuniversity.minf.stundenplaner.service;

import de.hofuniversity.minf.stundenplaner.common.exception.NotFoundException;
import de.hofuniversity.minf.stundenplaner.persistence.permission.data.PermissionDO;
import de.hofuniversity.minf.stundenplaner.persistence.permission.data.PermissionTypeEnum;
import de.hofuniversity.minf.stundenplaner.persistence.role.RoleRepository;
import de.hofuniversity.minf.stundenplaner.persistence.role.data.RoleDO;
import de.hofuniversity.minf.stundenplaner.persistence.role.data.RoleTypeEnum;
import de.hofuniversity.minf.stundenplaner.service.to.PermissionTO;
import de.hofuniversity.minf.stundenplaner.service.to.RoleTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private RoleServiceImpl roleService;

    private final RoleDO ROLE_DO_1 = new RoleDO(1L, RoleTypeEnum.ADMIN, Collections.emptySet());
    private final RoleDO ROLE_DO_2 = new RoleDO(2L, RoleTypeEnum.LECTURER, Collections.emptySet());

    @Test
    void testGetAll() {
        when(roleRepository.findAll()).thenReturn(List.of(ROLE_DO_1, ROLE_DO_2));
        List<RoleDO> expected = List.of(ROLE_DO_1, ROLE_DO_2);
        List<RoleTO> roleTOS = roleService.findAll();

        Assertions.assertEquals(expected.size(), roleTOS.size());

        List<PermissionTypeEnum> actualTypeEnumList = new ArrayList<>();
        roleTOS.forEach(roleTO -> roleTO.getPermissions().stream().map(PermissionTO::getType).forEach(s -> actualTypeEnumList.add(PermissionTypeEnum.valueOf(s))));

        List<PermissionTypeEnum> expectedTypeEnumList = new ArrayList<>();
        expected.forEach(roleDO -> roleDO.getPermissionDOs().stream().map(PermissionDO::getType).forEach(expectedTypeEnumList::add));

        Assertions.assertArrayEquals(
                expectedTypeEnumList.toArray(),
                actualTypeEnumList.toArray()
        );
    }

    @Test
    void testGetById() {
        when(roleRepository.findById(1L)).thenReturn(Optional.of(ROLE_DO_1));
        when(roleRepository.findById(3L)).thenReturn(Optional.empty());

        Assertions.assertEquals(ROLE_DO_1.getId(), roleService.findById(1L).getId());
        Assertions.assertThrows(NotFoundException.class, () -> roleService.findById(3L));
    }
}
