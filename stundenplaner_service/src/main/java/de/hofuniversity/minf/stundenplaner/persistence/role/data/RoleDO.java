package de.hofuniversity.minf.stundenplaner.persistence.role.data;

import de.hofuniversity.minf.stundenplaner.persistence.permission.data.PermissionDO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Set;

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

    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    private RoleTypeEnum type;

    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinTable(name = "t_role_permission_map", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id"))
    private Set<PermissionDO> permissionDOs;

}
