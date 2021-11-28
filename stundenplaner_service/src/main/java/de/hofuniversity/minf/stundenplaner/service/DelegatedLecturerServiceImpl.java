package de.hofuniversity.minf.stundenplaner.service;

import de.hofuniversity.minf.stundenplaner.persistence.account.DelegatedLecturerRepository;
import de.hofuniversity.minf.stundenplaner.persistence.account.LecturerProfileRepository;
import de.hofuniversity.minf.stundenplaner.persistence.account.data.ProfileDO;
import de.hofuniversity.minf.stundenplaner.persistence.user.UserRepository;
import de.hofuniversity.minf.stundenplaner.persistence.user.data.UserDO;
import de.hofuniversity.minf.stundenplaner.service.to.DelegatedLecturerTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author mheckel
 * Implementation for all business logic regarding the rooms
 */
@Service
public class DelegatedLecturerServiceImpl implements DelegatedLecturerService {

    private DelegatedLecturerRepository delegatedLecturerRepository;
    private LecturerProfileRepository lecturerProfileRepository;
    private UserRepository lecturerRepository;

    @Autowired
    public DelegatedLecturerServiceImpl(DelegatedLecturerRepository delegatedLecturerRepository,
                                        LecturerProfileRepository lecturerProfileRepository,
                                        UserRepository lecturerRepository){
        this.delegatedLecturerRepository = delegatedLecturerRepository;
        this.lecturerProfileRepository = lecturerProfileRepository;
        this.lecturerRepository = lecturerRepository;
    }

    @Override
    public List<DelegatedLecturerTO> getAllDelegatedLecturersForLecturer(Long lecturerId) {
        UserDO lecturer = findLecturerDOById(lecturerId);
        ProfileDO lecturerProfile = lecturerProfileRepository.findByLecturer(lecturer);
        return delegatedLecturerRepository.findAllByLecturerProfiles(lecturerProfile).stream()
                .map(DelegatedLecturerTO::fromDO)
                .collect(Collectors.toList());
    }

    @Override
    public DelegatedLecturerTO findById(Long id) {
        return DelegatedLecturerTO.fromDO(delegatedLecturerRepository.findById(id).orElse(null));
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
