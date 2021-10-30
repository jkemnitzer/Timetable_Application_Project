package de.hofuniversity.minf.stundenplaner.service;

import de.hofuniversity.minf.stundenplaner.common.NotFoundException;
import de.hofuniversity.minf.stundenplaner.persistence.lecture.LessonDO;
import de.hofuniversity.minf.stundenplaner.persistence.lecture.LessonRepository;
import de.hofuniversity.minf.stundenplaner.service.boundary.LessonService;
import de.hofuniversity.minf.stundenplaner.service.to.LessonTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;

    @Autowired
    public LessonServiceImpl(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Override
    public List<LessonTO> getAllLessons() {
        return StreamSupport
                .stream(lessonRepository.findAll().spliterator(), false)
                .map(LessonTO::fromDO)
                .collect(Collectors.toList());
    }

    @Override
    public LessonTO findById(Long id) {
        Optional<LessonDO> optional = lessonRepository.findById(id);
        if (optional.isPresent()) {
            return LessonTO.fromDO(optional.get());
        } else {
            throw new NotFoundException(LessonDO.class, id);
        }
    }

    @Override
    public LessonTO createLesson(LessonTO lessonTo) {
        LessonDO lessonDO = LessonDO.fromTO(lessonTo);

        lessonDO.setId(null);
        return LessonTO.fromDO(lessonRepository.save(lessonDO));
    }


    @Override
    public LessonTO updateLesson(Long idLesson, LessonTO lessonTo) {
        Optional<LessonDO> optional = lessonRepository.findById(idLesson);
        if (optional.isPresent()) {
            LessonDO lessonDO=optional.get();
            lessonDO.update(lessonTo);
            lessonRepository.save(lessonDO);
            return LessonTO.fromDO(lessonDO);
        } else {
            throw new NotFoundException(LessonDO.class, idLesson);
        }
    }

    @Override
    public LessonTO removeLesson(Long id) {
        Optional<LessonDO> optional = lessonRepository.findById(id);
        if (optional.isPresent()) {
            LessonDO lessonDO = optional.get();
            lessonRepository.delete(lessonDO);
            return LessonTO.fromDO(lessonDO);
        } else {
            throw new NotFoundException(LessonDO.class, id);
        }
    }
}
