package de.hofuniversity.minf.stundenplaner.persistence.account.data;

import de.hofuniversity.minf.stundenplaner.persistence.timetable.timeslot.data.TimeslotDO;
import de.hofuniversity.minf.stundenplaner.service.to.TimeSlotPreferenceTO;
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
@Table(name = "t_timeslot_preference")
public class TimeSlotPreferenceDO {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "t_timeslot_preference_seq")
    @SequenceGenerator(name = "t_timeslot_preference_seq", sequenceName = "t_timeslot_preference_seq", allocationSize = 1)
    private Long id;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "forced")
    private Boolean forced;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fk_timeslot_id", nullable = false)
    private TimeslotDO timeSlot;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fk_profile_id", nullable = false)
    private ProfileDO lecturerProfile;

    public static TimeSlotPreferenceDO fromTO(TimeSlotPreferenceTO timeSlotPreferenceTO) {
        return new TimeSlotPreferenceDO(
                timeSlotPreferenceTO.getId(),
                timeSlotPreferenceTO.getPriority(),
                timeSlotPreferenceTO.getForced(),
                TimeslotDO.fromTO(timeSlotPreferenceTO.getTimeSlot()),
                null
        );
    }
}

