package de.hofuniversity.minf.stundenplaner.persistence.room.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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
