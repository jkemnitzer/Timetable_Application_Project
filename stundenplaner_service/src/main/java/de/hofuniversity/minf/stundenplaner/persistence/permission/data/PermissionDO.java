package de.hofuniversity.minf.stundenplaner.persistence.permission.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author mbaecker
 * <p>
 * Basic POJO class for table representation of permission
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_permission")
public class PermissionDO {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "t_permission_seq")
    @SequenceGenerator(name = "t_permission_seq", sequenceName = "t_permission_seq", allocationSize = 1)
    private Long id;

    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    private PermissionTypeEnum type;

}
