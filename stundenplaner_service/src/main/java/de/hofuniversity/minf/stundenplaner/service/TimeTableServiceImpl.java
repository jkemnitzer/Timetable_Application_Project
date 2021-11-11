package de.hofuniversity.minf.stundenplaner.service;

import de.hofuniversity.minf.stundenplaner.common.NotFoundException;
import de.hofuniversity.minf.stundenplaner.persistence.lecture.LectureRepository;
import de.hofuniversity.minf.stundenplaner.persistence.lecture.data.LectureDO;
import de.hofuniversity.minf.stundenplaner.persistence.room.RoomRepository;
import de.hofuniversity.minf.stundenplaner.persistence.room.data.RoomDO;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.TimeTableRepository;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.data.LessonDO;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.timeslot.TimeslotRepository;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.timeslot.data.TimeslotDO;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.version.TimeTableVersionDO;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.version.TimeTableVersionRepository;
import de.hofuniversity.minf.stundenplaner.persistence.user.UserRepository;
import de.hofuniversity.minf.stundenplaner.persistence.user.data.UserDO;
import de.hofuniversity.minf.stundenplaner.service.boundary.TimeTableService;
import de.hofuniversity.minf.stundenplaner.service.to.LessonTO;
import de.hofuniversity.minf.stundenplaner.service.to.TimeTableTO;
import de.hofuniversity.minf.stundenplaner.service.to.TimeTableVersionTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class TimeTableServiceImpl implements TimeTableService {

    private final TimeTableRepository timeTableRepository;
    private final TimeTableVersionRepository versionRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final LectureRepository lectureRepository;
    private final TimeslotRepository timeslotRepository;

    @Autowired
    public TimeTableServiceImpl(
            TimeTableRepository timeTableRepository,
            TimeTableVersionRepository versionRepository,
            UserRepository userRepository,
            RoomRepository roomRepository,
            LectureRepository lectureRepository,
            TimeslotRepository timeslotRepository) {
        this.timeTableRepository = timeTableRepository;
        this.versionRepository = versionRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.lectureRepository = lectureRepository;
        this.timeslotRepository = timeslotRepository;
    }

    @Override
    public List<LessonTO> findAllLessons() {
        return StreamSupport.stream(timeTableRepository.findAll().spliterator(), false)
                .map(LessonTO::fromDO)
                .toList();
    }

    @Override
    public TimeTableTO findAllLessonsByVersion(Long versionId) {
        Optional<TimeTableVersionDO> optional = versionRepository.findById(versionId);
        if (optional.isPresent()) {
            TimeTableVersionDO versionDO = optional.get();
            return new TimeTableTO(
                    TimeTableVersionTO.fromDO(versionDO),
                    timeTableRepository.findAllByTimeTableVersionDO(versionDO).stream()
                            .map(LessonTO::fromDO)
                            .toList()
            );
        } else {
            throw new NotFoundException(TimeTableVersionDO.class, versionId);
        }
    }

    @Override
    public TimeTableTO createTimeTable(TimeTableTO timeTableTO) {
        TimeTableVersionDO versionDO = TimeTableVersionDO.fromTO(timeTableTO.getVersion());
        versionDO.setId(null);
        TimeTableVersionTO versionTO = TimeTableVersionTO.fromDO(versionRepository.save(versionDO));
        List<LessonDO> lessonDOs = timeTableTO.getLessons().stream()
                .map(to -> {
                    LessonDO lessonDO = LessonDO.fromTO(to);
                    lessonDO.setId(null);
                    to.setVersionId(versionTO.getId());
                    setDependencies(lessonDO, to);
                    return lessonDO;
                }).toList();
        List<LessonTO> lessonTOs = StreamSupport
                .stream(timeTableRepository.saveAll(lessonDOs).spliterator(), false)
                .map(LessonTO::fromDO)
                .toList();
        return new TimeTableTO(
            versionTO,
            lessonTOs
        );
    }

    @Override
    public TimeTableTO deleteTimeTable(Long versionId) {
        Optional<TimeTableVersionDO> optional = versionRepository.findById(versionId);
        if (optional.isPresent()) {
            TimeTableVersionDO versionDO = optional.get();
            List<LessonDO> lessonDOs = timeTableRepository.findAllByTimeTableVersionDO(versionDO);
            timeTableRepository.deleteAll(lessonDOs);
            versionRepository.delete(versionDO);
            return new TimeTableTO(
                    TimeTableVersionTO.fromDO(versionDO),
                    lessonDOs.stream()
                            .map(LessonTO::fromDO)
                            .toList()
            );
        } else {
            throw new NotFoundException(TimeTableVersionDO.class, versionId);
        }
    }

    @Override
    public LessonTO createSingleLesson(LessonTO lessonTO) {
        LessonDO lessonDO = LessonDO.fromTO(lessonTO);
        lessonDO.setId(null);
        this.setDependencies(lessonDO,lessonTO);
        timeTableRepository.save(lessonDO);
        return LessonTO.fromDO(lessonDO);
    }

    @Override
    public LessonTO findSingleLesson(Long id) {
        Optional<LessonDO> optional = timeTableRepository.findById(id);
        if (optional.isPresent()){
            return LessonTO.fromDO(optional.get());
        } else {
            throw new NotFoundException(LessonDO.class, id);
        }
    }

    @Override
    public LessonTO deleteLesson(Long id) {
        Optional<LessonDO> optional = timeTableRepository.findById(id);
        if (optional.isPresent()){
            LessonDO lessonDO = optional.get();
            timeTableRepository.delete(lessonDO);
            return LessonTO.fromDO(lessonDO);
        } else {
            throw new NotFoundException(LessonDO.class, id);
        }
    }

    @Override
    public LessonTO updateLesson(Long id, LessonTO lessonTO) {
        Optional<LessonDO> optional = timeTableRepository.findById(id);
        if (optional.isPresent()) {
            LessonDO lessonDO = optional.get();
            lessonDO.updateFromTO(lessonTO);
            this.setDependencies(lessonDO, lessonTO);
            return LessonTO.fromDO(timeTableRepository.save(lessonDO));
        } else {
            throw new NotFoundException(LessonDO.class, id);
        }
    }

    private void setDependencies(LessonDO lessonDO, LessonTO lessonTO){
        LectureDO lectureDO = lectureRepository.findById(lessonTO.getLectureId())
                .orElseThrow(()->new NotFoundException(LectureDO.class, lessonTO.getLectureId()));
        UserDO userDO = userRepository.findById(lessonTO.getLecturerId())
                .orElseThrow(()->new NotFoundException(UserDO.class, lessonTO.getLecturerId()));
        RoomDO roomDO = roomRepository.findById(lessonTO.getRoomId())
                .orElseThrow(()->new NotFoundException(RoomDO.class, lessonTO.getRoomId()));
        TimeslotDO timeslotDO = timeslotRepository.findById(lessonTO.getTimeslotId())
                .orElseThrow(()->new NotFoundException(TimeslotDO.class, lessonTO.getTimeslotId()));
        TimeTableVersionDO versionDO = versionRepository.findById(lessonTO.getVersionId())
                .orElseThrow(()->new NotFoundException(TimeTableVersionDO.class, lessonTO.getVersionId()));
        lessonDO.setLectureDO(lectureDO);
        lessonDO.setLecturerDO(userDO);
        lessonDO.setRoomDO(roomDO);
        lessonDO.setTimeslotDO(timeslotDO);
        lessonDO.setTimeTableVersionDO(versionDO);
    }
}
