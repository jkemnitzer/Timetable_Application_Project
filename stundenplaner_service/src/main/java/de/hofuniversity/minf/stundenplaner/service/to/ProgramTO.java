package de.hofuniversity.minf.stundenplaner.service.to;

import de.hofuniversity.minf.stundenplaner.persistence.program.data.ProgramDO;

public class ProgramTO {

    private Long id;

    public static ProgramTO fromDO(ProgramDO programDO) {
        return new ProgramTO();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
