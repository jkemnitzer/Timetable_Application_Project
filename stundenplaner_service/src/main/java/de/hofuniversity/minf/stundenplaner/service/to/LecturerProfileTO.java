package de.hofuniversity.minf.stundenplaner.service.to;

import de.hofuniversity.minf.stundenplaner.persistence.account.data.DelegatedLecturerDO;
import de.hofuniversity.minf.stundenplaner.persistence.account.data.ModulePreferenceDO;
import de.hofuniversity.minf.stundenplaner.persistence.account.data.ProfileDO;
import de.hofuniversity.minf.stundenplaner.persistence.account.data.TimeSlotPreferenceDO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LecturerProfileTO {

    private Long id;
    private List<Long> delegatedLecturers;
    private List<Long> modulePreferences;
    private List<Long> timeSlotPreferences;
    private UserTO lecturer;
    private Integer sws;

    public static LecturerProfileTO fromDO(ProfileDO lecturerProfileDO) {
        return new LecturerProfileTO(
                lecturerProfileDO.getId(),
                lecturerProfileDO.getDelegatedLecturers().stream()
                        .map(DelegatedLecturerDO::getId)
                        .collect(Collectors.toList()),
                lecturerProfileDO.getModulePreferences().stream()
                        .map(ModulePreferenceDO::getId)
                        .collect(Collectors.toList()),
                lecturerProfileDO.getTimeSlotPreferences().stream()
                        .map(TimeSlotPreferenceDO::getId)
                        .collect(Collectors.toList()),
                UserTO.fromDO(lecturerProfileDO.getLecturer()),
                lecturerProfileDO.getSws()
        );
    }

}
