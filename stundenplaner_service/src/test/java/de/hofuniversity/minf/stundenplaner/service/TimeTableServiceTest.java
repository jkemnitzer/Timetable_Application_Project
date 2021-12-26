package de.hofuniversity.minf.stundenplaner.service;

import de.hofuniversity.minf.stundenplaner.common.NotFoundException;
import de.hofuniversity.minf.stundenplaner.persistence.lecture.LectureRepository;
import de.hofuniversity.minf.stundenplaner.persistence.lecture.data.LectureDO;
import de.hofuniversity.minf.stundenplaner.persistence.room.RoomRepository;
import de.hofuniversity.minf.stundenplaner.persistence.room.data.RoomDO;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.TimeTableRepository;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.data.LessonDO;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.data.LessonType;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.timeslot.TimeslotRepository;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.timeslot.data.TimeslotDO;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.version.TimeTableVersionDO;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.version.TimeTableVersionRepository;
import de.hofuniversity.minf.stundenplaner.persistence.user.UserRepository;
import de.hofuniversity.minf.stundenplaner.persistence.user.data.UserDO;
import de.hofuniversity.minf.stundenplaner.service.to.LessonTO;
import de.hofuniversity.minf.stundenplaner.service.to.TimeTableTO;
import de.hofuniversity.minf.stundenplaner.service.to.TimeTableVersionTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TimeTableServiceTest {

    @Mock
    private TimeTableRepository timeTableRepository;
    @Mock
    private TimeTableVersionRepository versionRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private LectureRepository lectureRepository;
    @Mock
    private TimeslotRepository timeslotRepository;
    @InjectMocks
    private TimeTableServiceImpl timeTableService;

    private static final String S = "Test";
    private static final Long L = 1L;
    private static final LocalDateTime D = LocalDateTime.now();
    private static final LocalTime T = LocalTime.now();
    private static final Integer I = 1;
    private static final RoomDO ROOM = new RoomDO(L, S, S);
    private static final UserDO USER = new UserDO(L, S, S, S, S, S, S, S, D, D, null, Collections.emptySet(), null);
    private static final LectureDO LECTURE = new LectureDO(L, S, Collections.emptyList(), Collections.emptyList());
    private static final TimeslotDO TIMESLOT = new TimeslotDO(L, T, T, I);
    private static final TimeTableVersionDO VERSION = new TimeTableVersionDO(L, S, S, S);
    private static final LessonDO LESSON_1 = new LessonDO(1L, LessonType.LECTURE, S, ROOM, USER, LECTURE, TIMESLOT, VERSION);
    private static final LessonDO LESSON_2 = new LessonDO(2L, LessonType.LECTURE, S, ROOM, USER, LECTURE, TIMESLOT, VERSION);

    @Test
    public void findAllLessons() {
        when(timeTableRepository.findAll()).thenReturn(List.of(LESSON_1, LESSON_2));
        assertEquals(2, timeTableService.findAllLessons(1L, 1L, 1, 1L, 1L, LocalTime.now(), LocalTime.now()).size());
    }

    @Test
    public void findAllLessonsByVersion() {
        when(timeTableRepository.findAll()).thenReturn(List.of(LESSON_1, LESSON_2));
        when(versionRepository.findById(anyLong())).thenReturn(Optional.of(new TimeTableVersionDO()));
        TimeTableTO timeTableTO = timeTableService.findAllLessonsByVersion(1L, 1L, 1, 1L, 1L, LocalTime.now(), LocalTime.now());
        assertEquals(2, timeTableTO.getLessons().size());
        assertNotNull(timeTableTO.getVersion());
    }

    @Test
    public void createTimeTable() {
        when(timeTableRepository.saveAll(any())).thenReturn(List.of(LESSON_1, LESSON_2));
        when(versionRepository.save(any(TimeTableVersionDO.class))).thenReturn(VERSION);
        mockDependencies();
        TimeTableTO actual = timeTableService.createTimeTable(new TimeTableTO(
                TimeTableVersionTO.fromDO(VERSION), List.of(LessonTO.fromDO(LESSON_1), LessonTO.fromDO(LESSON_2))
        ));
        assertEquals(2, actual.getLessons().size());
        assertNotNull(actual.getVersion());
    }

    @Test
    public void deleteTimeTable() {
        when(timeTableRepository.findAllByTimeTableVersionDO(any(TimeTableVersionDO.class)))
                .thenReturn(List.of(LESSON_1, LESSON_2));
        when(versionRepository.findById(anyLong())).thenReturn(Optional.of(new TimeTableVersionDO()), Optional.empty());
        TimeTableTO actual = timeTableService.deleteTimeTable(1L);
        assertEquals(2, actual.getLessons().size());
        assertNotNull(actual.getVersion());
        assertThrows(NotFoundException.class, () -> timeTableService.deleteTimeTable(1L));
    }

    @Test
    public void createSingleLesson() {
        mockDependencies();
        when(timeTableRepository.save(any(LessonDO.class))).thenReturn(LESSON_1);
        LessonTO test = LessonTO.fromDO(LESSON_1);
        assertNotNull(timeTableService.createSingleLesson(test));
    }

    @Test
    public void findSingleLesson() {
        when(timeTableRepository.findById(anyLong())).thenReturn(Optional.of(LESSON_1));
        assertNotNull(timeTableService.findSingleLesson(1L));
    }

    @Test
    public void deleteLesson() {
        when(timeTableRepository.findById(anyLong())).thenReturn(Optional.of(LESSON_1), Optional.empty());
        assertNotNull(timeTableService.deleteLesson(1L));
        assertThrows(NotFoundException.class, () -> timeTableService.deleteLesson(1L));
    }

    @Test
    public void updateLesson() {
        final String TEST = "updated";
        when(timeTableRepository.findById(anyLong())).thenReturn(Optional.of(LESSON_1));
        when(timeTableRepository.save(any(LessonDO.class))).thenReturn(LESSON_1);
        mockDependencies();
        LessonTO test = LessonTO.fromDO(LESSON_1);
        test.setNote(TEST);
        assertEquals(TEST, timeTableService.updateLesson(1L, test).getNote());
    }

    private void mockDependencies() {
        when(versionRepository.findById(eq(L))).thenReturn(Optional.of(VERSION));
        when(userRepository.findById(eq(L))).thenReturn(Optional.of(USER));
        when(roomRepository.findById(eq(L))).thenReturn(Optional.of(ROOM));
        when(lectureRepository.findById(eq(L))).thenReturn(Optional.of(LECTURE));
        when(timeslotRepository.findById(eq(L))).thenReturn(Optional.of(TIMESLOT));
    }

}
