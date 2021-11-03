package de.hofuniversity.minf.stundenplaner.service.boundary;


import de.hofuniversity.minf.stundenplaner.service.to.LectureTO;
import de.hofuniversity.minf.stundenplaner.service.to.LessonTO;

import java.util.List;


public interface LectureService {

    List<LectureTO> getAllLectures();
    LectureTO findById(Long id);
    LectureTO createLecture(LectureTO lectureTo);
    LectureTO addLesson(Long idLecture,Long idLesson);
    LectureTO updateLecture(Long id, LectureTO lectureTo);
    LectureTO removeLecture(Long id);

}
