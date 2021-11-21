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
    private LecturerProfileTO lecturerProfile;
    //TODO: Add reference to the module

    public static ModulePreferenceTO fromDO(ModulePreferenceDO modulePreferenceDO) {
        return new ModulePreferenceTO(
                modulePreferenceDO.getId(),
                modulePreferenceDO.getPriority(),
                LecturerProfileTO.fromDO(modulePreferenceDO.getLecturerProfile())//TODO: Add reference to the profile of the delegated lecturer
        );
    }

}
