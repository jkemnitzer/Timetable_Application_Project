package de.hofuniversity.minf.stundenplaner.persistence.account.data;

import de.hofuniversity.minf.stundenplaner.service.to.ModulePreferenceTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


/**
 * @author mheckel
 * Basic class for table representation of delegated lecturers (lecturers that read a part of the professors SWS)
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_module_preference")
public class ModulePreferenceDO {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "t_module_preference_seq")
    @SequenceGenerator(name = "t_module_preference_seq", sequenceName = "t_module_preference_seq", allocationSize = 1)
    private Long id;

    @Column(name = "priority")
    private Integer priority;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fk_profile_id", nullable = false)
    private ProfileDO lecturerProfile;

    //TODO: Add reference to module


    public static ModulePreferenceDO fromTO(ModulePreferenceTO modulePreferenceTO) {
        return new ModulePreferenceDO(
                modulePreferenceTO.getId(),
                modulePreferenceTO.getPriority(),
                null//TODO: Add reference to the module
        );
    }
}

