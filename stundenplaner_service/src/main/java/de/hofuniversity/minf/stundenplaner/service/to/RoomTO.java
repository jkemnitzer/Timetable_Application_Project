package de.hofuniversity.minf.stundenplaner.service.to;

import de.hofuniversity.minf.stundenplaner.persistence.room.data.FeatureDO;
import de.hofuniversity.minf.stundenplaner.persistence.room.data.RoomDO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

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
    private List<FeatureTO> featureList;
    private Integer seats;
    private String location;


    public static RoomTO fromDO(RoomDO roomDO){
        return new RoomTO(
                roomDO.getId(),
                roomDO.getRoomNumber(),
                roomDO.getBuilding(),
                roomDO.getFeatureList().stream()
                        .map(FeatureTO::fromDO)
                        .collect(Collectors.toList()),
                roomDO.getSeats(),
                roomDO.getLocation()

        );

    }


}
