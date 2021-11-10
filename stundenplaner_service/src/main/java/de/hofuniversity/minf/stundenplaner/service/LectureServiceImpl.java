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

import java.util.ArrayList;
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
        List<LessonDO> lessons = lectureDO.getLessons();
        lectureDO.setLessons(new ArrayList<>());
        LectureDO newLectureDO = lectureRepository.save(lectureDO);
        lessons.forEach(lessonDO -> {
            lessonDO.setLecture(newLectureDO);
            lessonDO.setId(null);
            lessonRepository.save(lessonDO);
        } );
        lectureDO.setLessons(lessons);
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
    public LectureTO updateLecture(Long idLecture, LectureTO lectureTo, Boolean checkLessons) {
        Optional<LectureDO> optional = lectureRepository.findById(idLecture);
        if (optional.isPresent()) {
            LectureDO lectureDO=optional.get();
            lectureDO.update(lectureTo);
            if (checkLessons) {
                mergeLessons(lectureDO, lectureTo.getLessons());
            }
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
    private void mergeLessons(LectureDO lectureDO, List<LessonTO> newLessons) {
        List<Long> identical = new ArrayList<>();
        List<Long> toBeRemoved = new ArrayList<>();
        List<Long> newIds = newLessons.stream().map(LessonTO::getId).collect(Collectors.toList());
        lectureDO.getLessons().stream()
                .map(LessonDO::getId)
                .forEach(id -> {
                    if (newIds.contains(id)) {
                        identical.add(id);
                    } else {
                        toBeRemoved.add(id);
                    }
                });
        removeLessonFromLecture(lectureDO, toBeRemoved);
        newLessons.stream()
                .filter(to -> !identical.contains(to.getId()))
                .map(LessonDO::fromTO)
                .forEach(lessonDO -> {
                    lessonDO.setLecture(lectureDO);
                    lectureDO.getLessons().add(lessonRepository.save(lessonDO));
                });
    }
    private void removeLessonFromLecture(LectureDO lectureDO, List<Long> lessons) {
        List<LessonDO> toBeRemoved = findLessonsByIds(lectureDO, lessons);
        lectureDO.getLessons().removeAll(toBeRemoved);
        lessonRepository.deleteAll(toBeRemoved);
    }
    private List<LessonDO> findLessonsByIds(LectureDO lecture, List<Long> lessonsIds) {
        return lessonRepository.findAllByLectureAndIdIn(lecture, lessonsIds);
    }
}
