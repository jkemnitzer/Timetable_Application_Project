package de.hofuniversity.minf.stundenplaner.service;

import de.hofuniversity.minf.stundenplaner.persistence.account.LecturerProfileRepository;
import de.hofuniversity.minf.stundenplaner.persistence.account.TimeSlotPreferenceRepository;
import de.hofuniversity.minf.stundenplaner.persistence.account.data.ProfileDO;
import de.hofuniversity.minf.stundenplaner.persistence.user.UserRepository;
import de.hofuniversity.minf.stundenplaner.persistence.user.data.UserDO;
import de.hofuniversity.minf.stundenplaner.service.to.TimeSlotPreferenceTO;
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
public class TimeSlotPreferenceServiceImpl implements TimeSlotPreferenceService {

    private TimeSlotPreferenceRepository timeSlotPreferenceRepository;
    private LecturerProfileRepository lecturerProfileRepository;
    private UserRepository lecturerRepository;

    @Autowired
    public TimeSlotPreferenceServiceImpl(TimeSlotPreferenceRepository timeSlotPreferenceRepository,
                                         LecturerProfileRepository lecturerProfileRepository,
                                         UserRepository lecturerRepository){
        this.timeSlotPreferenceRepository = timeSlotPreferenceRepository;
        this.lecturerProfileRepository = lecturerProfileRepository;
        this.lecturerRepository = lecturerRepository;
    }

    @Override
    public List<TimeSlotPreferenceTO> getAllTimeSlotPreferencesForLecturer(Long lecturerId) {
        UserDO lecturer = findLecturerDOById(lecturerId);
        ProfileDO lecturerProfile = lecturerProfileRepository.findByLecturer(lecturer);
        return timeSlotPreferenceRepository.findAllByLecturerProfile(lecturerProfile).stream()
                .map(TimeSlotPreferenceTO::fromDO)
                .collect(Collectors.toList());
    }

    @Override
    public TimeSlotPreferenceTO findById(Long id) {
        return TimeSlotPreferenceTO.fromDO(timeSlotPreferenceRepository.findById(id).orElse(null));
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
