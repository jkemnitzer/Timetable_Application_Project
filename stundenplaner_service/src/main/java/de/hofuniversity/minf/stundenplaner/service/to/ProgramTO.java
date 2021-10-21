package de.hofuniversity.minf.stundenplaner.service.to;

import de.hofuniversity.minf.stundenplaner.persistence.program.data.ProgramDO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProgramTO {

    private Long id;
    private String name;

    public static ProgramTO fromDO(ProgramDO programDO) {
        return new ProgramTO(programDO.getId(), programDO.getName());
    }

}
