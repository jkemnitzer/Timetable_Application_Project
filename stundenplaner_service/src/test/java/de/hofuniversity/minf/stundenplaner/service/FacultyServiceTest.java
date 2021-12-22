package de.hofuniversity.minf.stundenplaner.service;

import de.hofuniversity.minf.stundenplaner.common.exception.NotFoundException;
import de.hofuniversity.minf.stundenplaner.persistence.faculty.FacultyRepository;
import de.hofuniversity.minf.stundenplaner.persistence.faculty.data.FacultyDO;
import de.hofuniversity.minf.stundenplaner.service.to.FacultyTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FacultyServiceTest {

    @Mock
    private FacultyRepository facultyRepository;
    @InjectMocks
    private FacultyServiceImpl facultySerice;

    private final FacultyDO FACULTY_DO_1 = new FacultyDO(1L, "Informatik");
    private final FacultyDO FACULTY_DO_2 = new FacultyDO(2L, "Wirtschaft");

    @Test
    void testGetAllFaculties() {
        when(facultyRepository.findAll()).thenReturn(List.of(FACULTY_DO_1, FACULTY_DO_2));
        List<FacultyDO> expected = List.of(FACULTY_DO_1, FACULTY_DO_2);
        List<FacultyTO> facultyTOs = facultySerice.getAllFaculties();

        Assertions.assertEquals(expected.size(), facultyTOs.size());
        Assertions.assertArrayEquals(expected.stream().map(FacultyDO::getName).toArray(),
                facultyTOs.stream().map(FacultyTO::getName).toArray());
    }

    @Test
    void testGetById() {
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(FACULTY_DO_1));
        when(facultyRepository.findById(3L)).thenReturn(Optional.empty());

        Assertions.assertEquals(FACULTY_DO_1.getId(), facultySerice.findById(1L).getId());
        Assertions.assertThrows(NotFoundException.class, () -> facultySerice.findById(3L));

    }

}
