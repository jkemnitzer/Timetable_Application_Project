package de.hofuniversity.minf.stundenplaner.service.to;

import de.hofuniversity.minf.stundenplaner.persistence.permission.data.PermissionDO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PermissionTO {

    private Long id;
    private String type;

    public static PermissionTO fromDO(PermissionDO permissionDO) {
        return new PermissionTO(
                permissionDO.getId(),
                permissionDO.getType().toString()
        );
    }

}
