package de.hofuniversity.minf.stundenplaner.persistence.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author nlehmann
 *
 * Basic POJO class for table representation of rooms
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rooms")
public class RoomDO {

    @Id
    private Long id;
    @Column(name = "number")
    private String roomNumber;
    @Column(name = "building")
    private String building;
}

