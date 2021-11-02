package de.hofuniversity.minf.stundenplaner.service.to;

import de.hofuniversity.minf.stundenplaner.persistence.role.data.RoleDO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleTO {

    private Long id;
    private String name;

    public static RoleTO fromDO(RoleDO roleDO) {
        return new RoleTO(
                roleDO.getId(),
                roleDO.getName()
        );
    }

}

