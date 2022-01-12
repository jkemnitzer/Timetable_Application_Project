package de.hofuniversity.minf.stundenplaner.persistence.account.data;

import de.hofuniversity.minf.stundenplaner.persistence.faculty.data.FacultyDO;
import de.hofuniversity.minf.stundenplaner.persistence.user.data.UserDO;
import de.hofuniversity.minf.stundenplaner.service.to.LecturerProfileTO;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Collections;
import java.util.List;

/**
 * @author mheckel
 * Basic POJO class for table representation of accounts
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_profile")
public class ProfileDO {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "t_profile_seq")
    @SequenceGenerator(name = "t_profile_seq", sequenceName = "t_profile_seq", allocationSize = 1)
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinTable(
            name="t_profile_delegated_lecturer_map",
            joinColumns = @JoinColumn(name="fk_profile_id"),
            inverseJoinColumns = @JoinColumn(name="fk_delegated_lecturer_id")
    )
    private List<DelegatedLecturerDO> delegatedLecturers;

    @OneToMany(mappedBy = "lecturerProfile", fetch = FetchType.LAZY)
    private List<ModulePreferenceDO> modulePreferences;

    @OneToMany(mappedBy = "lecturerProfile", fetch = FetchType.LAZY)
    private List<TimeSlotPreferenceDO> timeSlotPreferences;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user_id", nullable = false)
    private UserDO lecturer;

    @Column(name = "sws")
    private Integer sws;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_faculty_id", nullable = false)
    private FacultyDO faculty;

    public static ProfileDO fromTO(LecturerProfileTO lecturerProfileTO) {
        return new ProfileDO(
                lecturerProfileTO.getId(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                UserDO.fromTO(lecturerProfileTO.getLecturer()),
                lecturerProfileTO.getSws(),
                null
        );
    }

}

