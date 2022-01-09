package de.hofuniversity.minf.stundenplaner.service.criteria;

import de.hofuniversity.minf.stundenplaner.persistence.timetable.data.LessonDO;
import de.hofuniversity.minf.stundenplaner.persistence.user.data.UserDO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractCriterionLecturer extends AbstractCriterionMapped<UserDO>{

    /**
     * Maps given list of lessons to it's lecturer
     *
     * @param lessonsToEvaluate the list of lesson to be evaluated
     *
     * @return map containing the list of lesson (value) held by a lecturer (key)
     */
    public Map<UserDO, List<LessonDO>> getLessonMap(List<LessonDO> lessonsToEvaluate) {
        // create empty-map
        Map<UserDO, List<LessonDO>> mappedLessons = new HashMap<>();

        // loop over them and start mapping them
        for (LessonDO lesson : lessonsToEvaluate) {
            UserDO lecturer = lesson.getLecturerDO();
            // init arraylist once
            if (!mappedLessons.containsKey(lecturer)) {
                mappedLessons.put(lecturer, new ArrayList<LessonDO>());
            }
            // append the lesson
            mappedLessons.get(lecturer).add(lesson);
        }

        return mappedLessons; // done
    }
}
