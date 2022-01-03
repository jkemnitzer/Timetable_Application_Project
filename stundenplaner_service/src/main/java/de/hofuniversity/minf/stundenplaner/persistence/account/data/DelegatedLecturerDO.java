package de.hofuniversity.minf.stundenplaner.persistence.account.data;

import de.hofuniversity.minf.stundenplaner.persistence.user.data.UserDO;
import de.hofuniversity.minf.stundenplaner.service.to.DelegatedLecturerTO;
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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Collections;
import java.util.List;


/**
 * @author mheckel
 * Basic class for table representation of delegated lecturers (lecturers that read a part of the professors SWS)
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_delegated_lecturer")
public class DelegatedLecturerDO {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "t_delegated_lecturer_seq")
    @SequenceGenerator(name = "t_delegated_lecturer_seq", sequenceName = "t_delegated_lecturer_seq", allocationSize = 1)
    private Long id;

    @Column(name = "sws")
    private Integer sws;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy="delegatedLecturers")
    private List<ProfileDO> lecturerProfiles;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fk_user_id", nullable = false)
    private UserDO delegatedLecturer;


    public static DelegatedLecturerDO fromTO(DelegatedLecturerTO delegatedLecturerTO) {
        return new DelegatedLecturerDO(
                delegatedLecturerTO.getId(),
                delegatedLecturerTO.getSws(),
                Collections.emptyList(),
                null
        );
    }
}

