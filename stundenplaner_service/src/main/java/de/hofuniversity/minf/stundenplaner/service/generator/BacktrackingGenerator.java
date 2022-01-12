package de.hofuniversity.minf.stundenplaner.service.generator;

import de.hofuniversity.minf.stundenplaner.persistence.room.data.RoomDO;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.data.LessonDO;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.timeslot.data.TimeslotDO;
import de.hofuniversity.minf.stundenplaner.persistence.user.data.UserDO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mheckel
 * <p>
 * Basic implementation of a backtracking algorithm generating timetables based on the evaluation functions.
 * <p>
 * Currently, the generation is implemented in three steps to reduce the value space of the combinations: First, the
 * lecturers are assigned to the lessons that should be hold. Next, time slots are assigned to the lessons, and last,
 * rooms are assigned. This is the procedure currently done manually. It might be better to iterate over all three
 * combination criteria at the same time. However, I have not found a good way to do this yet.
 * <p>
 * At the moment the assignment algorithm works like this:
 * - Iterate over the list of lessons that should be assigned
 * - Iterate over the list of things that should be assigned to the lessons (e.g. lecturers, timeslots, rooms)
 * - Assign each item to the lesson of the current iteration and remove the lesson from the list (now, it is assigned)
 * - Verify that the assignment is valid (e.g. the stuff named "hard criteria" in the documentation)
 * - Recursively perform the assignment on the missing lessons again
 * - Verify that the recursive call was able to assign all lessons (otherwise, the combination is invalid as well)
 * - Check if the total value (recursive call and assignment at the current level) are better than the other
 * combinations checked until then. If this is the case, use the current assignment
 * - Return the assignment with the maximal value
 * <p>
 * This approach has the disadvantage that there is a huge number of combinations (it tries to brute-force all valid
 * combinations). Even though the invalid combinations are directly removed (and not processed recursively), there is
 * still a huge amount of valid combinations expected. Depending on the performance of the algorithm, it might be a
 * good idea to reduce the number of combinations the following way:
 * Select items (e.g. lecturers, time slots, rooms) and count only valid combinations (e.g. all lessons can be assigned
 * recursively and the current assignment is valid). Stop after a specified number of valid combinations (e.g. 5), so
 * the maximum of those valid combinations will be used instead of the global maximum. This leads to the best combination
 * out of e.g. 5 to be selected rather than the best combination out of all. However, it should reduce the amount of
 * options significantly.
 * <p>
 * In order to avoid deterministic generation (e.g. there is always the same generated timetable returned), the order
 * of the items selected should be randomized beforehand, so that another generator run generates another timetable. This
 * can be used afterwards to, for example, generate 10 timetables and choose the one with the best score (because the
 * "reduced" algorith optimizes for a local rather than a global maximum, so the next run should return another maximum
 * which might be better than the first one.
 * <p>
 * One extreme case of that would be to set the number of valid combinations to be evaluated to 1 which would, effectively,
 * return exactly one valid (random) timetable. Afterwards, multiple of those timetables could be generated and compared
 * to each other.
 * <p>
 * So Long, and Thanks for All the Fish
 */
public class BacktrackingGenerator implements Generator {
    private final int maxValidCombinations;
    private List<LessonDO> lessonsToAdd;
    private List<UserDO> lecturers;
    private List<TimeslotDO> timeslots;
    private List<RoomDO> rooms;

    public BacktrackingGenerator(List<LessonDO> lessonsToAdd, List<UserDO> lecturers, List<TimeslotDO> timeslots, List<RoomDO> rooms, int maxValidCombinations) {
        this.lessonsToAdd = lessonsToAdd;
        this.lecturers = lecturers;
        this.timeslots = timeslots;
        this.rooms = rooms;
        this.maxValidCombinations = maxValidCombinations;
    }

    private List<LessonDO> generateLecturerRecursive(List<LessonDO> lessonsToAdd) {
        Backtracker<UserDO> backtracker = new Backtracker<>(maxValidCombinations);
        ParamHelper<UserDO> lecturerParamSetter = new LecturerParamHelper();
        return backtracker.backtrack(lessonsToAdd, this.lecturers, new ArrayList<LessonDO>(), lecturerParamSetter);
    }

    private List<LessonDO> generateTimeslotRecursive(List<LessonDO> lessonsToAdd) {
        Backtracker<TimeslotDO> backtracker = new Backtracker<>(maxValidCombinations);
        ParamHelper<TimeslotDO> timeslotParamSetter = new TimeslotParamHelper();
        return backtracker.backtrack(lessonsToAdd, this.timeslots, new ArrayList<LessonDO>(), timeslotParamSetter);
    }

    private List<LessonDO> generateRoomRecursive(List<LessonDO> lessonsToAdd) {
        Backtracker<RoomDO> backtracker = new Backtracker<>(maxValidCombinations);
        ParamHelper<RoomDO> roomParamSetter = new RoomParamHelper();
        return backtracker.backtrack(lessonsToAdd, this.rooms, new ArrayList<LessonDO>(), roomParamSetter);
    }


    @Override
    public List<LessonDO> generate() {
        List<LessonDO> timetable = new ArrayList<>();

        // Here, some "fireback" effects should be considered: Maybe, one stage of generation returns an optimal
        // combination which results in the next stage of the generation being unable to find any valid combination.
        // For this reason, it is evaluated if the returned timetable is empty (e.g. it was not possible to generate
        // a valid timetable) and, if that is the case, the generation is done again.
        while (timetable.size() != lessonsToAdd.size()) {
            timetable = generateLecturerRecursive(lessonsToAdd);
            timetable = generateTimeslotRecursive(timetable);
            timetable = generateRoomRecursive(timetable);
        }

        return timetable;
    }
}
