package de.hofuniversity.minf.stundenplaner.persistence.account;

import de.hofuniversity.minf.stundenplaner.persistence.account.data.ProfileDO;
import de.hofuniversity.minf.stundenplaner.persistence.user.data.UserDO;
import org.springframework.data.repository.CrudRepository;

public interface LecturerProfileRepository extends CrudRepository<ProfileDO, Long> {
    ProfileDO findByLecturer(UserDO lecturer);
}
