package de.hofuniversity.minf.stundenplaner.service.to;

import de.hofuniversity.minf.stundenplaner.persistence.room.data.RoomDO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author KMP
 *
 *
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomTO {

    private Long id;
    private String number;
    private String building;

    public static RoomTO fromDO(RoomDO roomDO){
        return new RoomTO(
                roomDO.getId(),
                roomDO.getRoomNumber(),
                roomDO.getBuilding()
        );

    }


}
