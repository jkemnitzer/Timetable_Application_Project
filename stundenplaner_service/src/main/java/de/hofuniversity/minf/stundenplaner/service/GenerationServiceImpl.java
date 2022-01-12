package de.hofuniversity.minf.stundenplaner.service;

import de.hofuniversity.minf.stundenplaner.persistence.lecture.LectureRepository;
import de.hofuniversity.minf.stundenplaner.persistence.lecture.data.LectureDO;
import de.hofuniversity.minf.stundenplaner.persistence.program.ProgramRepository;
import de.hofuniversity.minf.stundenplaner.persistence.program.data.ProgramDO;
import de.hofuniversity.minf.stundenplaner.persistence.program.data.SemesterDO;
import de.hofuniversity.minf.stundenplaner.persistence.room.RoomRepository;
import de.hofuniversity.minf.stundenplaner.persistence.room.data.RoomDO;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.data.LessonDO;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.data.LessonType;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.timeslot.TimeslotRepository;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.timeslot.data.TimeslotDO;
import de.hofuniversity.minf.stundenplaner.persistence.user.UserRepository;
import de.hofuniversity.minf.stundenplaner.persistence.user.data.UserDO;
import de.hofuniversity.minf.stundenplaner.service.generator.BacktrackingGenerator;
import de.hofuniversity.minf.stundenplaner.service.generator.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

/**
 * @author mheckel
 * Implementation for all business logic regarding the rooms
 */
@Service
public class GenerationServiceImpl implements GenerationService {
    private Generator generator;
    private UserRepository userRepository;
    private TimeslotRepository timeslotRepository;
    private RoomRepository roomRepository;
    private ProgramRepository programRepository;

    @Autowired
    public GenerationServiceImpl(UserRepository userRepository, TimeslotRepository timeslotRepository, RoomRepository roomRepository, ProgramRepository programRepository) {
        this.userRepository = userRepository;
        this.timeslotRepository = timeslotRepository;
        this.roomRepository = roomRepository;
        this.programRepository = programRepository;

        // TODO: Add a list of all lectures that should take place in the semester
        List<LessonDO> lessonsToAdd = new ArrayList<>();
        for(ProgramDO program: programRepository.findAll()) {
            for(SemesterDO semester: program.getSemesterDOs()) {
                char[] lastChar = {'0'};
                semester.getNumber().getChars(semester.getNumber().length() - 1, semester.getNumber().length(), lastChar, 0);
                if(lastChar[0] % 2 == 1) {
                    for (LectureDO lecture : semester.getLectures()) {
                        System.out.println(lecture.getName());
                        LessonDO lesson = new LessonDO();
                        lesson.setLectureDO(lecture);
                        lesson.setLessonType(LessonType.LECTURE);
                        lessonsToAdd.add(lesson);
                    }
                }
            }
        }

        List<UserDO> lecturers = StreamSupport.stream(userRepository.findAll().spliterator(), false).toList();
        List<TimeslotDO> timeslots = StreamSupport.stream(timeslotRepository.findAll().spliterator(), false).toList();
        List<RoomDO> rooms = StreamSupport.stream(roomRepository.findAll().spliterator(), false).toList();

        generator = new BacktrackingGenerator(lessonsToAdd, lecturers, timeslots, rooms, 5);
    }

    @Override
    public List<LessonDO> generate() {
        return generator.generate();
    }
}
