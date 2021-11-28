package de.hofuniversity.minf.stundenplaner.service;

import de.hofuniversity.minf.stundenplaner.persistence.account.LecturerProfileRepository;
import de.hofuniversity.minf.stundenplaner.persistence.account.ModulePreferenceRepository;
import de.hofuniversity.minf.stundenplaner.persistence.account.data.ProfileDO;
import de.hofuniversity.minf.stundenplaner.persistence.user.UserRepository;
import de.hofuniversity.minf.stundenplaner.persistence.user.data.UserDO;
import de.hofuniversity.minf.stundenplaner.service.to.ModulePreferenceTO;
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
public class ModulePreferenceServiceImpl implements ModulePreferenceService {

    private ModulePreferenceRepository modulePreferenceRepository;
    private LecturerProfileRepository lecturerProfileRepository;
    private UserRepository lecturerRepository;


    @Autowired
    public ModulePreferenceServiceImpl(ModulePreferenceRepository modulePreferenceRepository,
                                       LecturerProfileRepository lecturerProfileRepository,
                                       UserRepository lecturerRepository){
        this.modulePreferenceRepository = modulePreferenceRepository;
        this.lecturerProfileRepository = lecturerProfileRepository;
        this.lecturerRepository = lecturerRepository;
    }

    @Override
    public List<ModulePreferenceTO> getAllModulePreferencesForLecturer(Long lecturerId) {
        UserDO lecturer = findLecturerDOById(lecturerId);
        ProfileDO lecturerProfile = lecturerProfileRepository.findByLecturer(lecturer);
        return modulePreferenceRepository.findAllByLecturerProfile(lecturerProfile).stream()
                .map(ModulePreferenceTO::fromDO)
                .collect(Collectors.toList());
    }

    @Override
    public ModulePreferenceTO findById(Long id) {
        return ModulePreferenceTO.fromDO(modulePreferenceRepository.findById(id).orElse(null));
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
