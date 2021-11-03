package de.hofuniversity.minf.stundenplaner.service;

import de.hofuniversity.minf.stundenplaner.common.NotFoundException;

import de.hofuniversity.minf.stundenplaner.persistence.lecture.LessonDO;
import de.hofuniversity.minf.stundenplaner.persistence.lecture.LessonRepository;
import de.hofuniversity.minf.stundenplaner.service.to.LessonTO;
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
public class LessonServiceTest {


    public static final long LESSON_ID = 1L;


    @Mock
    private LessonRepository lessonRepository;
    @InjectMocks
    private LessonServiceImpl LessonService;


    private static final LessonDO LESSON_DO_1 = new LessonDO(LESSON_ID, "OOP 1", "Atzenbeck",
            "PC-Raum", null);
    private static final LessonDO LESSON_DO_2 = new LessonDO(LESSON_ID, "OOP 1 Übung", "Atzenbeck",
            "PC-Raum", null);

    @Test
    public void testGetAll(){
        when(lessonRepository.findAll()).thenReturn(List.of(LESSON_DO_1, LESSON_DO_2));
        List<LessonDO> expected = List.of(LESSON_DO_1, LESSON_DO_2);
        List<LessonTO> LessonTOs = LessonService.getAllLessons();

        Assertions.assertEquals(expected.size(), LessonTOs.size());
        Assertions.assertArrayEquals(
                expected.stream().map(LessonDO::getLessonName).toArray(),
                LessonTOs.stream().map(LessonTO::getLessonName).toArray()
        );
    }

    @Test
    public void testFindById(){
        when(lessonRepository.findById(eq(LESSON_ID))).thenReturn(Optional.of(LESSON_DO_1));
        when(lessonRepository.findById(eq(3L))).thenReturn(Optional.empty());

        Assertions.assertEquals(LESSON_DO_1.getId(), LessonService.findById(LESSON_ID).getId());
        Assertions.assertThrows(NotFoundException.class, ()-> LessonService.findById(3L));
    }

    @Test
    public void testCreate(){
        when(lessonRepository.save(any(LessonDO.class))).thenReturn(LESSON_DO_1);
        LessonTO LessonTestTO = LessonTO.fromDO(LESSON_DO_1);
        LessonTestTO.setId(null);

        LessonTO actual = LessonService.createLesson(LessonTestTO);
        Assertions.assertEquals(LESSON_DO_1.getId(), actual.getId());
        Assertions.assertEquals(LESSON_DO_1.getLessonName(), actual.getLessonName());
    }

    @Test
    public void testUpdate(){
        when(lessonRepository.findById(eq(LESSON_ID))).thenReturn(Optional.of(
                new LessonDO(LESSON_DO_1.getId(), LESSON_DO_1.getLessonName(),LESSON_DO_1.getProf(),LESSON_DO_1.getRoomRequirement(),null)
        ));
        when(lessonRepository.findById(eq(3L))).thenReturn(Optional.empty());
        LessonTO test = LessonTO.fromDO(LESSON_DO_2);

        LessonTO actual = LessonService.updateLesson(LESSON_ID, test);
        Assertions.assertNotEquals(LESSON_DO_1.getLessonName(), actual.getLessonName());
        Assertions.assertEquals(LESSON_DO_2.getLessonName(), actual.getLessonName());

        Assertions.assertThrows(
                NotFoundException.class,
                ()-> LessonService.updateLesson(3L, LessonTO.fromDO(LESSON_DO_1))
        );
    }

    @Test
    public void testDelete(){
        when(lessonRepository.findById(eq(LESSON_ID))).thenReturn(Optional.of(LESSON_DO_1), Optional.empty());

        Assertions.assertEquals(LESSON_DO_1.getId(), LessonService.removeLesson(LESSON_ID).getId());
        Assertions.assertThrows(NotFoundException.class, ()-> LessonService.removeLesson(LESSON_ID));
    }


}
