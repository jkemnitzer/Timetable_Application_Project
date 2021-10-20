package de.hofuniversity.minf.stundenplaner.persistence.program.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "t_program_semester")
public class SemesterDO {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "number")
    private String number;

    @Column(name = "exp_participants")
    private int expectedParticipants;

    @Column(name = "act_participants")
    private int actualParticipants;

}
