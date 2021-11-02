package de.hofuniversity.minf.stundenplaner.persistence.role.data;

import de.hofuniversity.minf.stundenplaner.service.to.RoleTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author mbaecker
 * <p>
 * Basic POJO class for table representation of role
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_role")
public class RoleDO {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "t_role_seq")
    @SequenceGenerator(name = "t_role_seq", sequenceName = "t_role_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    public static RoleDO fromTO(RoleTO roleTO) {
        return new RoleDO(
                roleTO.getId(),
                roleTO.getName()
        );
    }

    /* To be continued when implementing the role concept. */
}
