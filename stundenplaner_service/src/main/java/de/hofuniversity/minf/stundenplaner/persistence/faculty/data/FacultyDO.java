package de.hofuniversity.minf.stundenplaner.persistence.faculty.data;

import de.hofuniversity.minf.stundenplaner.service.to.FacultyTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author: Jonas Kemnitzer Basic POJO class for faculty
 */

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_faculty")
public class FacultyDO {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "t_faculty_seq")
    @SequenceGenerator(name = "t_faculty_seq", sequenceName = "t_faculty_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    public static FacultyDO fromTO(FacultyTO facultyTO) {
        return new FacultyDO(
                facultyTO.getId(),
                facultyTO.getName()
        );
    }

}
