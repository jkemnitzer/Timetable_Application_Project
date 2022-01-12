package de.hofuniversity.minf.stundenplaner.persistence.program.data;

import de.hofuniversity.minf.stundenplaner.persistence.faculty.data.FacultyDO;
import de.hofuniversity.minf.stundenplaner.service.to.ProgramTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_program")
public class ProgramDO {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "t_program_seq")
    @SequenceGenerator(name = "t_program_seq", sequenceName = "t_program_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "program", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<SemesterDO> semesterDOs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_faculty_id", nullable = false)
    private FacultyDO faculty;


    public void updateFromTO(ProgramTO programTO) {
        this.setName(programTO.getName());
    }

    public static ProgramDO fromTO(ProgramTO programTo) {
        return new ProgramDO(
                programTo.getId(),
                programTo.getName(),
                programTo.getSemesters().stream()
                        .map(SemesterDO::fromTO)
                        .toList(),
                null
        );
    }
}
