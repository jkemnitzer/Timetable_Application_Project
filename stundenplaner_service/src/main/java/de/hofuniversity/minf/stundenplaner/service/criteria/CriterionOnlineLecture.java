package de.hofuniversity.minf.stundenplaner.service.criteria;

import de.hofuniversity.minf.stundenplaner.persistence.timetable.data.LessonDO;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.data.LessonType;
import de.hofuniversity.minf.stundenplaner.persistence.user.data.UserDO;

import java.util.HashMap;
import java.util.List;


/**
 * CriterionOnlineLecture defines the criterion for evaluating the online-lecture-type.
 *
 * It consists out of two-borders and therefore three fields:
 *  - percentage of online-lectures below the lower-border are wanted; A = {100.0}
 *  - percentage of online-lectures above the lower-border and below the upper-border are unwanted; A = {100.0, ..., 0.0}
 *  - percentage of online-lectures above the upper-border are extremely unwanted; A = {0.0}
 *
 * @author Jan Gaida
 * @task #6254
 */
public class CriterionOnlineLecture extends AbstractCriterionLecturer {

    // the name of this criterion
    public static final String CriterionName = "CriterionOnlineLecture";

    // the two defined border-values to calculate the evaluation with, based on the feedback
    // !! LOWER_BORDER_PERCENTAGE < UPPER_BORDER_PERCENTAGE
    // !! UPPER_BORDER_PERCENTAGE - LOWER_BORDER_PERCENTAGE == .1
    private static final double LOWER_BORDER_PERCENTAGE = .2;
    private static final double UPPER_BORDER_PERCENTAGE = .3;

    // as a work-around to give a solid explanation we'll store the calculated values instead of calculating them multiple times
    private HashMap<Long, Double> mappedOnlinePercentage = new HashMap<>();


    @Override
    public Double evaluateLessonsPerIdentifier(List<LessonDO> lessonsToEvaluate) {
        // count the online-lectures
        int onlineCounter = 0;
        for (LessonDO lesson : lessonsToEvaluate) {
            if (lesson.getLessonType() == LessonType.ONLINE_LECTURE) {
                onlineCounter += 1;
            }
        }
        // calc the percentage
        double onlinePercentage = onlineCounter / (double) lessonsToEvaluate.size();
        // key's won't be duplicated - see AbstractCriterionLecturer
        mappedOnlinePercentage.put(lessonsToEvaluate.get(0).getLecturerDO().getId(), onlinePercentage);
        // calc the evaluation
        if (onlinePercentage <= LOWER_BORDER_PERCENTAGE) {
            return 100.0; // all fine
        } else if (onlinePercentage >= UPPER_BORDER_PERCENTAGE) {
            return 0.0; // too much online
        } else {
            // i.e. lower-border = .2 && upper-border = .3
            // onlinePercentage: .21 ==> 90
            // onlinePercentage: .25 ==> 50
            // onlinePercentage: .29 ==> 10
            return 100 - ((onlinePercentage - LOWER_BORDER_PERCENTAGE) * (UPPER_BORDER_PERCENTAGE - LOWER_BORDER_PERCENTAGE) * 10000); // in between
        }
    }

    @Override
    public CriterionExplaination createExplanation(LessonDO lesson, Double value, UserDO lecturer) {
        CriterionExplaination criterionExplaination = new CriterionExplaination();
        criterionExplaination.criterionType = CriterionName;
        criterionExplaination.lesson = lesson;
        criterionExplaination.weight = value;

        criterionExplaination.placeholders = new HashMap<>();
        // Text: "Lecturer $lecturerName hold's $percentage % online-lectures."
        criterionExplaination.placeholders.put("lecturerName", lesson.getLecturerDO().getUsername());
        criterionExplaination.placeholders.put("$onlinePercentage", String.format("%.0f", this.mappedOnlinePercentage.get(lecturer.getId()) * 100));
        return criterionExplaination;
    }

    @Override
    public void cleanupBeforeEvaluation() {
        // clean the saved percentages
        this.mappedOnlinePercentage = new HashMap<>();
    }
}
