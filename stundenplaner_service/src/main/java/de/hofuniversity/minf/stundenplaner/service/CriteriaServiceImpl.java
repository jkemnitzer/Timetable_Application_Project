package de.hofuniversity.minf.stundenplaner.service;

import de.hofuniversity.minf.stundenplaner.persistence.lecture.LectureRepository;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.data.LessonDO;
import de.hofuniversity.minf.stundenplaner.service.boundary.TimeTableService;
import de.hofuniversity.minf.stundenplaner.service.criteria.Criteria;
import de.hofuniversity.minf.stundenplaner.service.criteria.CriterionExplaination;
import de.hofuniversity.minf.stundenplaner.service.to.LessonTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mheckel
 * Implementation for all business logic regarding the rooms
 */
@Service
public class CriteriaServiceImpl implements CriteriaService {

    private final Criteria criteria;
    private final LectureRepository lectureRepository;

    @Autowired
    public CriteriaServiceImpl(LectureRepository lectureRepository) {
        criteria = Criteria.getInstance();
        this.lectureRepository = lectureRepository;
    }

    private List<LessonDO> getLessonDOByVersion(TimeTableService timeTableService, Long timeTableId) {
        List<Long> LessonIDs = timeTableService.findAllLessonsByVersion(timeTableId).getLessons().stream().map(LessonTO::getId).toList();
        List<LessonDO> lessons = new ArrayList<>();

        for(Long id: LessonIDs) {
            lectureRepository.findById(id);
        }

        return lessons;
    }

    public Double evaluateTimeTable (TimeTableService timeTableService, Long timeTableId) {

        return criteria.evaluate(getLessonDOByVersion(timeTableService, timeTableId));
    }

    public List<CriterionExplaination> explainTimeTable(TimeTableService timeTableService, Long timeTableId) {
        return criteria.explain(getLessonDOByVersion(timeTableService, timeTableId));
    }

}
