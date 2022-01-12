package de.hofuniversity.minf.stundenplaner.service.generator;

import de.hofuniversity.minf.stundenplaner.persistence.timetable.data.LessonDO;
import de.hofuniversity.minf.stundenplaner.service.criteria.Criteria;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author mheckel
 */
public class Backtracker<T> {
    private final Criteria criteria;
    private final int maxValidCombinations;

    public Backtracker(int maxValidCombinations) {
        criteria = Criteria.getInstance();
        this.maxValidCombinations = maxValidCombinations;
    }

    public List<LessonDO> backtrack(List<LessonDO> lessonsToAdd, List<T> itemsToAdd, List<LessonDO> timetable, ParamHelper<T> paramHelper) {
        List<LessonDO> generatedLessons = new ArrayList<>();

        // maxValue is initialized to be negative in order to get at least one valid combination (if possible)
        double maxValue = -10.0;
        int toAddLectures = 0;
        int validCombinations = 0;

        // randomize the order of lecturers
        Collections.shuffle(itemsToAdd);

        for(LessonDO lesson: lessonsToAdd) {
            if(lesson != null) {
                toAddLectures++;
            }
        }

        LessonDO bestLesson = null;
        int bestLessonIndex = 0;

        for(int i = 0; i < lessonsToAdd.size(); i++) {
            LessonDO lesson = lessonsToAdd.get(i);

            if(lesson == null) {
                // The lesson is null and, therefore, was already added before (should be ignored)
                continue;
            }

            if(paramHelper.getParam(lesson) != null) {
                // The parameter is already specified and is not required to be guessed
                generatedLessons.add(lesson);
                lessonsToAdd.set(i, null);
                continue;
            }

            for(T item: itemsToAdd) {
                // Assign the current item to the lecture and remove it from the list
                paramHelper.setParam(lesson, item);
                lessonsToAdd.set(i, null);
                timetable.add(lesson);

                // Check if the list is still valid when the item is mapped to the lecture (verify "hard" criteria)
                double value = criteria.evaluate(timetable);
                if(value == Double.NEGATIVE_INFINITY) {
                    // Stop recursion when a combination is invalid
                    paramHelper.setParam(lesson, null);
                    timetable.remove(lesson);
                    lessonsToAdd.set(i, lesson);
                    continue;
                }

                // Continue the recursion
                List<LessonDO> addedLessons = backtrack(lessonsToAdd, itemsToAdd, timetable, paramHelper);
                if(addedLessons.size() != toAddLectures - 1) {
                    // None of the following recursions was able to add lecturers for all lessons, so this combination is invalid
                    paramHelper.setParam(lesson, null);
                    timetable.remove(lesson);
                    lessonsToAdd.set(i, lesson);
                    continue;
                }

                // The combination returned by the recursive call is valid (all required lectures were assigned)
                timetable.addAll(addedLessons);

                // Search the maximum value (verify "soft" criteria)
                value = criteria.evaluate(timetable);
                if(value > maxValue) {
                    maxValue = value;

                    // Undo last modifications (remove the lecturer for the combination that was rated best before)
                    if(bestLesson != null) {
                        paramHelper.setParam(bestLesson, null);
                        timetable.remove(bestLesson);
                        lessonsToAdd.set(bestLessonIndex, bestLesson);
                    }

                    bestLesson = lesson;
                    bestLessonIndex = i;

                    generatedLessons = timetable;
                } else {
                    // undo modification
                    paramHelper.setParam(lesson, null);
                    timetable.remove(lesson);
                    lessonsToAdd.set(i, lesson);
                }
                timetable.remove(lesson);

                if(validCombinations++ >= maxValidCombinations) {
                    return generatedLessons;
                }
            }
        }
        return generatedLessons;
    }
}
