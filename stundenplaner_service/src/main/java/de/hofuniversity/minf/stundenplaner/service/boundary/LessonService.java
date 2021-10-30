package de.hofuniversity.minf.stundenplaner.service.boundary;


import de.hofuniversity.minf.stundenplaner.service.to.LessonTO;

import java.util.List;


public interface LessonService {

    List<LessonTO> getAllLessons();
    LessonTO findById(Long id);
    LessonTO createLesson (LessonTO lessonTo);
    LessonTO updateLesson(Long id, LessonTO lessonTo);
    LessonTO removeLesson(Long id);

}
