package de.hofuniversity.minf.stundenplaner.persistence.account;

import de.hofuniversity.minf.stundenplaner.persistence.account.data.DelegatedLecturerDO;
import de.hofuniversity.minf.stundenplaner.persistence.account.data.ProfileDO;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DelegatedLecturerRepository extends CrudRepository<DelegatedLecturerDO, Long> {
    List<DelegatedLecturerDO> findAllByLecturerProfiles(ProfileDO lecturerProfile);
}
