package de.hofuniversity.minf.stundenplaner.service.to;

import de.hofuniversity.minf.stundenplaner.persistence.account.data.TimeSlotPreferenceDO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimeSlotPreferenceTO {

    private Long id;
    private Integer priority;
    private TimeslotTO timeSlot;
    private LecturerProfileTO lecturerProfile;

    public static TimeSlotPreferenceTO fromDO(TimeSlotPreferenceDO timeSlotPreferenceDO) {
        return new TimeSlotPreferenceTO(
                timeSlotPreferenceDO.getId(),
                timeSlotPreferenceDO.getPriority(),
                TimeslotTO.fromDO(timeSlotPreferenceDO.getTimeSlot()),
                LecturerProfileTO.fromDO(timeSlotPreferenceDO.getLecturerProfile())
        );
    }

}
