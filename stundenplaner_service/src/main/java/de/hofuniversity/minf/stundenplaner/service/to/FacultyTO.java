package de.hofuniversity.minf.stundenplaner.service.to;

import de.hofuniversity.minf.stundenplaner.persistence.faculty.data.FacultyDO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Jonas Kemnitzer
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FacultyTO {

    private Long id;
    private String name;

    public static FacultyTO fromDO(FacultyDO facultyDO) {
        return new FacultyTO(facultyDO.getId(), facultyDO.getName());
    }
}
