package de.hofuniversity.minf.stundenplaner.service.to;


import de.hofuniversity.minf.stundenplaner.persistence.room.data.FeatureDO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author KMP
 *
 */


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeatureTO {

    private Long id;
    private String feature;

    public static FeatureTO fromDO(FeatureDO featureDO) {
        return new FeatureTO(
                featureDO.getId(),
                featureDO.getFeature()
        );
    }

}
