package de.hofuniversity.minf.stundenplaner.service;

import de.hofuniversity.minf.stundenplaner.common.exception.NotFoundException;
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
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.BeforeEach;
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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
    private static final RoomDO ROOM = new RoomDO(L, S, S, Collections.emptyList(), I, S);
    private static final UserDO USER = new UserDO(L, S, S, S, S, S, S, S, D, D, null, Collections.emptySet(), null);
    private static final LectureDO LECTURE = new LectureDO(L, S, Collections.emptyList(), Collections.emptyList());
    private static final TimeslotDO TIMESLOT = new TimeslotDO(L, T, T, I);
    private static final TimeTableVersionDO VERSION = new TimeTableVersionDO(L, S, S, S);
    private static LessonDO LESSON_1;
    private static LessonDO LESSON_2;

    @BeforeEach
    public void resetLesson() {
        LESSON_1 = new LessonDO(1L, LessonType.LECTURE, S, ROOM, USER, LECTURE, TIMESLOT, VERSION);
        LESSON_2 = new LessonDO(2L, LessonType.LECTURE, S, ROOM, USER, LECTURE, TIMESLOT, VERSION);
    }

    @Test
    public void findAllLessons() {
        when(timeTableRepository.findAll()).thenReturn(List.of(LESSON_1, LESSON_2));
        assertEquals(2, timeTableService.findAllLessons().size());
    }

    @Test
    public void findAllLessonsByVersion() {
        when(timeTableRepository.findAllByTimeTableVersionDO(any())).thenReturn(List.of(LESSON_1, LESSON_2));
        when(versionRepository.findById(anyLong())).thenReturn(Optional.of(new TimeTableVersionDO()));
        TimeTableTO timeTableTO = timeTableService.findAllLessonsByVersion(1L);
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

    @Test
    public void exportAll() {
        when(timeTableRepository.findAll()).thenReturn(List.of(LESSON_1, LESSON_2));
        // test if no throw
        assertDoesNotThrow(() -> timeTableService.exportAll());
        // check values
        Workbook workbook = timeTableService.exportAll();
        Sheet sheet = workbook.getSheet("Stundenplan");
        assertNotNull(sheet);
        // row-0
        assertEquals("WeekDay", sheet.getRow(0).getCell(0).getStringCellValue());
        assertEquals("Lecturer Id", sheet.getRow(0).getCell(1).getStringCellValue());
        assertEquals("Start", sheet.getRow(0).getCell(2).getStringCellValue());
        assertEquals("Lesson Type", sheet.getRow(0).getCell(3).getStringCellValue());
        assertEquals("Room Id", sheet.getRow(0).getCell(4).getStringCellValue());
        assertEquals("Title", sheet.getRow(0).getCell(5).getStringCellValue());
        assertEquals("End", sheet.getRow(0).getCell(6).getStringCellValue());
        assertEquals("Id", sheet.getRow(0).getCell(7).getStringCellValue());
        assertEquals("Lecturer", sheet.getRow(0).getCell(8).getStringCellValue());
        assertEquals("Room", sheet.getRow(0).getCell(9).getStringCellValue());
        assertEquals("Timeslot Id", sheet.getRow(0).getCell(10).getStringCellValue());
        assertEquals("Lecture Id", sheet.getRow(0).getCell(11).getStringCellValue());
        // row-1
        assertEquals("1", sheet.getRow(1).getCell(0).getStringCellValue());
        assertEquals(String.valueOf(LESSON_1.getLecturerDO().getId()), sheet.getRow(1).getCell(1).getStringCellValue());
        assertEquals(String.valueOf(LESSON_1.getTimeslotDO().getStart()), sheet.getRow(1).getCell(2).getStringCellValue());
        assertEquals(String.valueOf(LESSON_1.getLessonType()), sheet.getRow(1).getCell(3).getStringCellValue());
        assertEquals(String.valueOf(LESSON_1.getRoomDO().getId()), sheet.getRow(1).getCell(4).getStringCellValue());
        assertEquals(String.valueOf(LESSON_1.getLectureDO().getName()), sheet.getRow(1).getCell(5).getStringCellValue());
        assertEquals(String.valueOf(LESSON_1.getTimeslotDO().getEnd()), sheet.getRow(1).getCell(6).getStringCellValue());
        assertEquals(String.valueOf(LESSON_1.getId()), sheet.getRow(1).getCell(7).getStringCellValue());
        assertEquals(LESSON_1.getLecturerDO().getTitle() + " " + LESSON_1.getLecturerDO().getFirstName() + " " + LESSON_1.getLecturerDO().getLastName(), sheet.getRow(1).getCell(8).getStringCellValue());
        assertEquals(LESSON_1.getRoomDO().getRoomNumber(), sheet.getRow(1).getCell(9).getStringCellValue());
        assertEquals(String.valueOf(LESSON_1.getTimeTableVersionDO().getId()), sheet.getRow(1).getCell(10).getStringCellValue());
        assertEquals(String.valueOf(LESSON_1.getLectureDO().getId()), sheet.getRow(1).getCell(11).getStringCellValue());
        // row-2
        assertEquals("1", sheet.getRow(2).getCell(0).getStringCellValue());
        assertEquals(String.valueOf(LESSON_2.getLecturerDO().getId()), sheet.getRow(2).getCell(1).getStringCellValue());
        assertEquals(String.valueOf(LESSON_2.getTimeslotDO().getStart()), sheet.getRow(2).getCell(2).getStringCellValue());
        assertEquals(String.valueOf(LESSON_2.getLessonType()), sheet.getRow(2).getCell(3).getStringCellValue());
        assertEquals(String.valueOf(LESSON_2.getRoomDO().getId()), sheet.getRow(2).getCell(4).getStringCellValue());
        assertEquals(String.valueOf(LESSON_2.getLectureDO().getName()), sheet.getRow(2).getCell(5).getStringCellValue());
        assertEquals(String.valueOf(LESSON_2.getTimeslotDO().getEnd()), sheet.getRow(2).getCell(6).getStringCellValue());
        assertEquals(String.valueOf(LESSON_2.getId()), sheet.getRow(2).getCell(7).getStringCellValue());
        assertEquals(LESSON_2.getLecturerDO().getTitle() + " " + LESSON_2.getLecturerDO().getFirstName() + " " + LESSON_2.getLecturerDO().getLastName(), sheet.getRow(2).getCell(8).getStringCellValue());
        assertEquals(LESSON_2.getRoomDO().getRoomNumber(), sheet.getRow(2).getCell(9).getStringCellValue());
        assertEquals(String.valueOf(LESSON_2.getTimeTableVersionDO().getId()), sheet.getRow(2).getCell(10).getStringCellValue());
        assertEquals(String.valueOf(LESSON_2.getLectureDO().getId()), sheet.getRow(2).getCell(11).getStringCellValue());
    }

    private void mockDependencies() {
        when(versionRepository.findById(eq(L))).thenReturn(Optional.of(VERSION));
        when(userRepository.findById(eq(L))).thenReturn(Optional.of(USER));
        when(roomRepository.findById(eq(L))).thenReturn(Optional.of(ROOM));
        when(lectureRepository.findById(eq(L))).thenReturn(Optional.of(LECTURE));
        when(timeslotRepository.findById(eq(L))).thenReturn(Optional.of(TIMESLOT));
    }

}
