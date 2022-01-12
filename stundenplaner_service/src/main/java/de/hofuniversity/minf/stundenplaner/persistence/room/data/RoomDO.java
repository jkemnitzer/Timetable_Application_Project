package de.hofuniversity.minf.stundenplaner.persistence.room.data;

import de.hofuniversity.minf.stundenplaner.service.to.RoomTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Collections;
import java.util.List;

/**
 * @author KMP
 * <p>
 * Basic POJO class for table representation of rooms
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_room")
public class RoomDO {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "t_room_seq")
    @SequenceGenerator(name = "t_room_seq", sequenceName = "t_room_seq", allocationSize = 1)
    private Long id;

    @Column(name = "number")
    private String roomNumber;

    @Column(name = "building")
    private String building;

    //@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_roomfeature_map",
            joinColumns = {@JoinColumn(name = "fk_room_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "fk_feature_id", referencedColumnName = "id")}
    )
    //@JoinTable(
    //        name="t_roomfeature_map",
    //        joinColumns = @JoinColumn(name="fk_room_id", referencedColumnName = "id"),
    //        inverseJoinColumns = @JoinColumn(name="fk_feature_id", referencedColumnName = "id")
    //)
    private List<FeatureDO> featureList;

    @Column(name = "seats")
    private Integer seats;


    @Column(name = "location")
    private String location;



    public void updateFromTO(RoomTO roomTO) {
        this.setRoomNumber(roomTO.getNumber());
        this.setBuilding(roomTO.getBuilding());
    }

    public static RoomDO fromTO(RoomTO roomTo) {
        return new RoomDO(
                roomTo.getId(),
                roomTo.getNumber(),
                roomTo.getBuilding(),
                Collections.emptyList(),
                roomTo.getSeats(),
                roomTo.getLocation()
        );
    }


}

