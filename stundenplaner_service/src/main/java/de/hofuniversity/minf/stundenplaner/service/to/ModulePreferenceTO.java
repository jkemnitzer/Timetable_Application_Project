package de.hofuniversity.minf.stundenplaner.service.to;

import de.hofuniversity.minf.stundenplaner.persistence.account.data.ModulePreferenceDO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModulePreferenceTO {

    private Long id;
    private Integer priority;
    private Long lecturerProfile;
    private Long lecture;

    public static ModulePreferenceTO fromDO(ModulePreferenceDO modulePreferenceDO) {
        return new ModulePreferenceTO(
                modulePreferenceDO.getId(),
                modulePreferenceDO.getPriority(),
                modulePreferenceDO.getLecturerProfile().getId(),
                modulePreferenceDO.getLecture().getId()
        );
    }

}
