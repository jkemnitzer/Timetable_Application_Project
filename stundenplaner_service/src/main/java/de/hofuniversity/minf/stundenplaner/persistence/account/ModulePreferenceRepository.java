package de.hofuniversity.minf.stundenplaner.persistence.account;

import de.hofuniversity.minf.stundenplaner.persistence.account.data.ProfileDO;
import de.hofuniversity.minf.stundenplaner.persistence.account.data.ModulePreferenceDO;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ModulePreferenceRepository extends CrudRepository<ModulePreferenceDO, Long> {
    List<ModulePreferenceDO> findAllByLecturerProfile(ProfileDO lecturerProfile);
}
