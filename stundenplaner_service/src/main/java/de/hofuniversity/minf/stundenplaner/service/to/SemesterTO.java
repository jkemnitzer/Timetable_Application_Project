package de.hofuniversity.minf.stundenplaner.service.to;

import de.hofuniversity.minf.stundenplaner.persistence.program.data.SemesterDO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SemesterTO {

    private Long id;
    private String number;
    private Integer expectedParticipants;
    private Integer actualParticipants;
    private Long programId;
    private Boolean accessibilityNeeded;

    public static SemesterTO fromDO(SemesterDO semesterDO) {
        return new SemesterTO(
                semesterDO.getId(),
                semesterDO.getNumber(),
                semesterDO.getExpectedParticipants(),
                semesterDO.getActualParticipants(),
                semesterDO.getProgram().getId(),
                semesterDO.getAccessibilityNeeded()
        );
    }

}
