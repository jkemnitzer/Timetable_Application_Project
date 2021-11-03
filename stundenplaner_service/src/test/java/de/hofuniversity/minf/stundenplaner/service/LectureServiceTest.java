package de.hofuniversity.minf.stundenplaner.service;

import de.hofuniversity.minf.stundenplaner.common.NotFoundException;
import de.hofuniversity.minf.stundenplaner.persistence.lecture.LectureDO;
import de.hofuniversity.minf.stundenplaner.persistence.lecture.LectureRepository;

import de.hofuniversity.minf.stundenplaner.persistence.lecture.LessonDO;
import de.hofuniversity.minf.stundenplaner.persistence.lecture.LessonRepository;
import de.hofuniversity.minf.stundenplaner.service.to.LectureTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LectureServiceTest {

    public static final long LECTURE_ID = 1L;
    public static final long LESSON_ID = 1L;

    @Mock
    private LectureRepository lectureRepository;
    @Mock
    private LessonRepository lessonRepository;
    @InjectMocks
    private LectureServiceImpl lectureService;

    private static final LectureDO LECTURE_DO_1 = new LectureDO(LECTURE_ID, "Betriebssysteme 1", new ArrayList<>());
    private static final LectureDO LECTURE_DO_2 = new LectureDO(2L, "Statistik", new ArrayList<>());
    private static final LessonDO LESSON_DO = new LessonDO(LESSON_ID, "OOP 1", "Atzenbeck",
                                                            "PC-Raum", LECTURE_DO_1);

    @Test
    public void testGetAll(){
        when(lectureRepository.findAll()).thenReturn(List.of(LECTURE_DO_1, LECTURE_DO_2));
        List<LectureDO> expected = List.of(LECTURE_DO_1, LECTURE_DO_2);
        List<LectureTO> lectureTOs = lectureService.getAllLectures();

        Assertions.assertEquals(expected.size(), lectureTOs.size());
        Assertions.assertArrayEquals(
                expected.stream().map(LectureDO::getLectureName).toArray(),
                lectureTOs.stream().map(LectureTO::getLectureName).toArray()
        );
    }

    @Test
    public void testFindById(){
        when(lectureRepository.findById(eq(LECTURE_ID))).thenReturn(Optional.of(LECTURE_DO_1));
        when(lectureRepository.findById(eq(3L))).thenReturn(Optional.empty());

        Assertions.assertEquals(LECTURE_DO_1.getId(), lectureService.findById(LECTURE_ID).getId());
        Assertions.assertThrows(NotFoundException.class, ()-> lectureService.findById(3L));
    }

    @Test
    public void testCreate(){
        when(lectureRepository.save(any(LectureDO.class))).thenReturn(LECTURE_DO_1);
        LectureTO lectureTestTO = LectureTO.fromDO(LECTURE_DO_1);
        lectureTestTO.setId(null);

        LectureTO actual = lectureService.createLecture(lectureTestTO);
        Assertions.assertEquals(LECTURE_DO_1.getId(), actual.getId());
        Assertions.assertEquals(LECTURE_DO_1.getLectureName(), actual.getLectureName());
    }

    @Test
    public void testUpdate(){
        when(lectureRepository.findById(eq(LECTURE_ID))).thenReturn(Optional.of(
                new LectureDO(LECTURE_DO_1.getId(), LECTURE_DO_1.getLectureName(), Collections.emptyList())
        ));
        when(lectureRepository.findById(eq(3L))).thenReturn(Optional.empty());
        LectureTO test = LectureTO.fromDO(LECTURE_DO_2);

        LectureTO actual = lectureService.updateLecture(LECTURE_ID, test);
        Assertions.assertNotEquals(LECTURE_DO_1.getLectureName(), actual.getLectureName());
        Assertions.assertEquals(LECTURE_DO_2.getLectureName(), actual.getLectureName());

        Assertions.assertThrows(
                NotFoundException.class,
                ()-> lectureService.updateLecture(3L, LectureTO.fromDO(LECTURE_DO_1))
        );
    }

    @Test
    public void testDelete(){
        when(lectureRepository.findById(eq(LECTURE_ID))).thenReturn(Optional.of(LECTURE_DO_1), Optional.empty());

        Assertions.assertEquals(LECTURE_DO_1.getId(), lectureService.removeLecture(LECTURE_ID).getId());
        Assertions.assertThrows(NotFoundException.class, ()-> lectureService.removeLecture(LECTURE_ID));
    }
    @Test
    public void testAddLesson(){
        when(lectureRepository.save(any(LectureDO.class))).thenReturn(LECTURE_DO_1);
        LectureTO lectureTestTO = LectureTO.fromDO(LECTURE_DO_1);
        lectureTestTO.setId(null);
        LectureTO actual = lectureService.createLecture(lectureTestTO);
        Assertions.assertEquals(LECTURE_DO_1.getId(), actual.getId());


        when(lectureRepository.findById(eq(LECTURE_ID))).thenReturn(Optional.of(LECTURE_DO_1));
        when(lessonRepository.findById(LESSON_ID)).thenReturn(Optional.of(LESSON_DO));

        Assertions.assertEquals(LESSON_ID, lectureService.addLesson(LECTURE_ID, LESSON_ID).getLessons().get(0).getId());
    }

}
