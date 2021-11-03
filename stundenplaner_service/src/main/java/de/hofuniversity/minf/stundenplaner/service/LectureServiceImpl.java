package de.hofuniversity.minf.stundenplaner.service;

import de.hofuniversity.minf.stundenplaner.common.NotFoundException;
import de.hofuniversity.minf.stundenplaner.persistence.lecture.LectureDO;
import de.hofuniversity.minf.stundenplaner.persistence.lecture.LectureRepository;
import de.hofuniversity.minf.stundenplaner.persistence.lecture.LessonDO;
import de.hofuniversity.minf.stundenplaner.persistence.lecture.LessonRepository;
import de.hofuniversity.minf.stundenplaner.service.boundary.LectureService;
import de.hofuniversity.minf.stundenplaner.service.to.LectureTO;
import de.hofuniversity.minf.stundenplaner.service.to.LessonTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class LectureServiceImpl implements LectureService {

    private final LectureRepository lectureRepository;
    private final LessonRepository lessonRepository;

    @Autowired
    public LectureServiceImpl(LectureRepository lectureRepository,LessonRepository lessonRepository) {
        this.lectureRepository = lectureRepository;
        this.lessonRepository=lessonRepository;
    }

    @Override
    public List<LectureTO> getAllLectures() {

        return StreamSupport
                .stream(lectureRepository.findAll().spliterator(), false)
                .map(LectureTO::fromDO)
                .collect(Collectors.toList());
    }

    @Override
    public LectureTO findById(Long id) {
        Optional<LectureDO> optional = lectureRepository.findById(id);
        if (optional.isPresent()) {
            return LectureTO.fromDO(optional.get());
        } else {
            throw new NotFoundException(LectureDO.class, id);
        }
    }

    @Override
    public LectureTO createLecture(LectureTO lectureTo) {
        LectureDO lectureDO = LectureDO.fromTO(lectureTo);
        lectureDO.setId(null);
        LectureDO newLectureDO = lectureRepository.save(lectureDO);
        lectureDO.getLessons().forEach(lessonDO -> {
            lessonDO.setLecture(newLectureDO);
            lessonDO.setId(null);
            lessonRepository.save(lessonDO);
        } );
        return LectureTO.fromDO(newLectureDO);
    }

    @Override
    @Transactional
    public LectureTO addLesson(Long idLecture,Long idLesson) {
        Optional<LectureDO> optional = lectureRepository.findById(idLecture);
        if (optional.isPresent()) {
            LectureDO lectureDO = optional.get();
            LessonDO savedLessonDO=lessonRepository.findById(idLesson).
                    orElseThrow(() -> new NotFoundException(LessonDO.class, idLesson));
            lectureDO.getLessons().add(savedLessonDO);
            savedLessonDO.setLecture(lectureDO);

            lectureRepository.save(lectureDO);
            return LectureTO.fromDO(lectureDO);
        } else {
            throw new NotFoundException(LectureDO.class, idLecture);
        }
    }

    @Override
    public LectureTO updateLecture(Long idLecture, LectureTO lectureTo) {
        Optional<LectureDO> optional = lectureRepository.findById(idLecture);
        if (optional.isPresent()) {
            LectureDO lectureDO=optional.get();
            lectureDO.update(lectureTo);
            lessonRepository.saveAll(lectureDO.getLessons());
            lectureRepository.save(lectureDO);
            return LectureTO.fromDO(lectureDO);
        } else {
            throw new NotFoundException(LectureDO.class, idLecture);
        }
    }

    @Override
    public LectureTO removeLecture(Long id) {
        Optional<LectureDO> optional = lectureRepository.findById(id);
        if (optional.isPresent()) {
            LectureDO lectureDO = optional.get();
            lessonRepository.deleteAll(lectureDO.getLessons());
            lectureRepository.delete(lectureDO);
            return LectureTO.fromDO(lectureDO);
        } else {
            throw new NotFoundException(LectureDO.class, id);
        }
    }
}
