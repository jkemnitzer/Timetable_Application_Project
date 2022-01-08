package de.hofuniversity.minf.stundenplaner.persistence.room.data;

import de.hofuniversity.minf.stundenplaner.persistence.permission.data.PermissionTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author KMP
 *
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_roomfeature")
public class FeatureDO {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "t_roomfeature_seq")
    @SequenceGenerator(name = "t_roomfeature_seq", sequenceName = "t_roomfeature_seq", allocationSize = 1)
    private Long id;

    @Column(name = "feature")
    private String feature;

}
