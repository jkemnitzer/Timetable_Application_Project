package de.hofuniversity.minf.stundenplaner.service.criteria;

import de.hofuniversity.minf.stundenplaner.persistence.timetable.data.LessonDO;

import java.util.Map;

public class CriterionExplaination {

    public String criterionType;
    public LessonDO lesson;
    public Map<String, String> placeholders;
    public Double weight;


}
