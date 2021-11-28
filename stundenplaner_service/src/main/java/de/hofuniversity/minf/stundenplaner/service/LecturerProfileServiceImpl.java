package de.hofuniversity.minf.stundenplaner.service;

import de.hofuniversity.minf.stundenplaner.persistence.account.LecturerProfileRepository;
import de.hofuniversity.minf.stundenplaner.persistence.user.UserRepository;
import de.hofuniversity.minf.stundenplaner.persistence.user.data.UserDO;
import de.hofuniversity.minf.stundenplaner.service.to.LecturerProfileTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author mheckel
 * Implementation for all business logic regarding the rooms
 */
@Service
public class LecturerProfileServiceImpl implements LecturerProfileService {

    private LecturerProfileRepository lecturerProfileRepository;
    private UserRepository lecturerRepository;

    @Autowired
    public LecturerProfileServiceImpl(LecturerProfileRepository lecturerProfileRepository,
                                      UserRepository lecturerRepository) {
        this.lecturerProfileRepository = lecturerProfileRepository;
        this.lecturerRepository = lecturerRepository;
    }

    @Override
    public LecturerProfileTO getLecturerProfileForLecturer(Long lecturerId) {
            UserDO lecturer = findLecturerDOById(lecturerId);
        return LecturerProfileTO.fromDO(lecturerProfileRepository.findByLecturer(lecturer));
    }

    @Override
    public LecturerProfileTO findById(Long id) {
        return LecturerProfileTO.fromDO(lecturerProfileRepository.findById(id).orElse(null));
    }

    public UserDO findLecturerDOById(Long lecturerId) {
        Optional<UserDO> optional = lecturerRepository.findById(lecturerId);
        if(optional.isPresent()) {
            return optional.get();
        }
        //TODO: Throw not found exception after merge
        return null;
    }
}
