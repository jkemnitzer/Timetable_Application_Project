package de.hofuniversity.minf.stundenplaner.service.criteria;

import de.hofuniversity.minf.stundenplaner.persistence.program.data.SemesterDO;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.data.LessonDO;
import de.hofuniversity.minf.stundenplaner.persistence.user.data.UserDO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractCriterionProgram extends AbstractCriterionMapped<String>{

    @Override
    public Map<String, List<LessonDO>> getLessonMap(List<LessonDO> lessonDOList) {
        Map<String, List<LessonDO>> lessonsPerCourse = new HashMap<>();

        for (LessonDO lesson : lessonDOList) {
            for (SemesterDO semester : lesson.getLectureDO().getSemesters()) {
                String identifier = semester.getProgram().getName().concat(semester.getNumber());
                boolean found = false;
                for (String key : lessonsPerCourse.keySet()) {
                    if (key.equals(identifier)) {
                        found = true;
                        lessonsPerCourse.get(key).add(lesson);
                    }
                }

                if (!found) {
                    List<LessonDO> list = new ArrayList<>();
                    list.add(lesson);
                    lessonsPerCourse.put(identifier, list);
                }
            }
        }

        return lessonsPerCourse;
    }
}
