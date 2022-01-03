package de.hofuniversity.minf.stundenplaner.persistence.program.data;

import de.hofuniversity.minf.stundenplaner.persistence.lecture.data.LectureDO;
import de.hofuniversity.minf.stundenplaner.service.to.SemesterTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_semester")
public class SemesterDO {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "t_semester_seq")
    @SequenceGenerator(name = "t_semester_seq", sequenceName = "t_semester_seq", allocationSize = 1)
    private Long id;

    @Column(name = "number")
    private String number;

    @Column(name = "exp_participants")
    private int expectedParticipants;

    @Column(name = "act_participants")
    private int actualParticipants;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "program_id", nullable = false)
    private ProgramDO program;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "t_semester_lecture_map", joinColumns = @JoinColumn(name = "fk_semester", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "fk_lecture", referencedColumnName = "id")
    )
    private List<LectureDO> lectures;

    @Column(name = "accessibility_needed")
    private boolean accessibility_needed;

    public void updateFromTO(SemesterTO semesterTO) {
        this.setNumber(semesterTO.getNumber());
        this.setExpectedParticipants(semesterTO.getExpectedParticipants());
        this.setActualParticipants(semesterTO.getActualParticipants());
    }

    public static SemesterDO fromTO(SemesterTO semesterTO) {
        return new SemesterDO(
                semesterTO.getId(),
                semesterTO.getNumber(),
                semesterTO.getExpectedParticipants(),
                semesterTO.getActualParticipants(),
                null,
                Collections.emptyList(),
                false
        );
    }
}
