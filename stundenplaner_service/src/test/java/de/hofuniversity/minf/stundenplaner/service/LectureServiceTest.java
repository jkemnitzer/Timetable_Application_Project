package de.hofuniversity.minf.stundenplaner.service;

import de.hofuniversity.minf.stundenplaner.common.exception.NotFoundException;
import de.hofuniversity.minf.stundenplaner.persistence.lecture.LectureRepository;
import de.hofuniversity.minf.stundenplaner.persistence.lecture.data.LectureDO;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.TimeTableRepository;
import de.hofuniversity.minf.stundenplaner.service.to.LectureTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LectureServiceTest {

    @Mock
    private LectureRepository lectureRepository;
    @Mock
    private TimeTableRepository timeTableRepository;
    @InjectMocks
    private LectureServiceImpl lectureService;

    public static final long LECTURE_ID = 1L;
    private static final LectureDO LECTURE_DO_1 = new LectureDO(LECTURE_ID, "Betriebssysteme 1", Collections.emptyList());
    private static final LectureDO LECTURE_DO_2 = new LectureDO(2L, "Statistik", Collections.emptyList());

    @Test
    void testGetAll(){
        when(lectureRepository.findAll()).thenReturn(List.of(LECTURE_DO_1, LECTURE_DO_2));
        List<LectureDO> expected = List.of(LECTURE_DO_1, LECTURE_DO_2);
        List<LectureTO> lectureTOs = lectureService.getAllLectures();

        Assertions.assertEquals(expected.size(), lectureTOs.size());
        Assertions.assertArrayEquals(
                expected.stream().map(LectureDO::getName).toArray(),
                lectureTOs.stream().map(LectureTO::getLectureName).toArray()
        );
    }

    @Test
    void testFindById(){
        when(lectureRepository.findById(LECTURE_ID)).thenReturn(Optional.of(LECTURE_DO_1));
        when(lectureRepository.findById(3L)).thenReturn(Optional.empty());

        Assertions.assertEquals(LECTURE_DO_1.getId(), lectureService.findById(LECTURE_ID).getId());
        Assertions.assertThrows(NotFoundException.class, ()-> lectureService.findById(3L));
    }

    @Test
    void testCreate(){
        when(lectureRepository.save(any(LectureDO.class))).thenReturn(LECTURE_DO_1);
        LectureTO lectureTestTO = LectureTO.fromDO(LECTURE_DO_1);
        lectureTestTO.setId(null);

        LectureTO actual = lectureService.createLecture(lectureTestTO);
        Assertions.assertEquals(LECTURE_DO_1.getId(), actual.getId());
        Assertions.assertEquals(LECTURE_DO_1.getName(), actual.getLectureName());
    }

    @Test
    void testUpdate(){
        when(lectureRepository.findById(LECTURE_ID)).thenReturn(Optional.of(
                new LectureDO(LECTURE_DO_1.getId(), LECTURE_DO_1.getName(), Collections.emptyList())
        ));
        when(lectureRepository.findById(3L)).thenReturn(Optional.empty());
        LectureTO test = LectureTO.fromDO(LECTURE_DO_2);

        LectureTO actual = lectureService.updateLecture(LECTURE_ID, test, true);
        Assertions.assertNotEquals(LECTURE_DO_1.getName(), actual.getLectureName());
        Assertions.assertEquals(LECTURE_DO_2.getName(), actual.getLectureName());

        Assertions.assertThrows(
                NotFoundException.class,
                ()-> lectureService.updateLecture(3L, test, true)
        );
    }

    @Test
    void testDelete(){
        when(lectureRepository.findById(LECTURE_ID)).thenReturn(Optional.of(LECTURE_DO_1), Optional.empty());

        Assertions.assertEquals(LECTURE_DO_1.getId(), lectureService.removeLecture(LECTURE_ID).getId());
        Assertions.assertThrows(NotFoundException.class, ()-> lectureService.removeLecture(LECTURE_ID));
    }

}
