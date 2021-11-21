package de.hofuniversity.minf.stundenplaner.service.to;

import de.hofuniversity.minf.stundenplaner.persistence.account.data.DelegatedLecturerDO;
import de.hofuniversity.minf.stundenplaner.persistence.account.data.ProfileDO;
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
public class DelegatedLecturerTO {

    private Long id;
    private Integer sws;
    private List<Long> lecturerProfiles;
    private UserTO delegatedLecturer;

    public static DelegatedLecturerTO fromDO(DelegatedLecturerDO delegatedLecturerDO) {
        return new DelegatedLecturerTO(
                delegatedLecturerDO.getId(),
                delegatedLecturerDO.getSws(),
                delegatedLecturerDO.getLecturerProfiles().stream()
                        .map(ProfileDO::getId)
                        .collect(Collectors.toList()),
                UserTO.fromDO(delegatedLecturerDO.getDelegatedLecturer())
        );
    }

}
