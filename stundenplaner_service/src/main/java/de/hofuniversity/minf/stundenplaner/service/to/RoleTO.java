package de.hofuniversity.minf.stundenplaner.service.to;

import de.hofuniversity.minf.stundenplaner.persistence.role.data.RoleDO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleTO {

    private Long id;
    private String type;
    private Set<PermissionTO> permissions;

    public static RoleTO fromDO(RoleDO roleDO) {
        return new RoleTO(
                roleDO.getId(),
                roleDO.getType().toString(),
                roleDO.getPermissionDOs().stream()
                        .map(PermissionTO::fromDO)
                        .collect(Collectors.toSet())
        );
    }

}

