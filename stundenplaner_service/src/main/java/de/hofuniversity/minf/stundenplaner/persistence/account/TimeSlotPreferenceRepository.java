package de.hofuniversity.minf.stundenplaner.persistence.account;

import de.hofuniversity.minf.stundenplaner.persistence.account.data.ProfileDO;
import de.hofuniversity.minf.stundenplaner.persistence.account.data.TimeSlotPreferenceDO;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TimeSlotPreferenceRepository extends CrudRepository<TimeSlotPreferenceDO, Long> {
    List<TimeSlotPreferenceDO> findAllByLecturerProfile(ProfileDO lecturerProfile);
}
