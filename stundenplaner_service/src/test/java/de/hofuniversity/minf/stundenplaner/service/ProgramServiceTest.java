package de.hofuniversity.minf.stundenplaner.service;

import de.hofuniversity.minf.stundenplaner.common.exception.NotFoundException;
import de.hofuniversity.minf.stundenplaner.persistence.program.ProgramRepository;
import de.hofuniversity.minf.stundenplaner.persistence.program.data.ProgramDO;
import de.hofuniversity.minf.stundenplaner.service.to.ProgramTO;
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
class ProgramServiceTest {

    @Mock
    private ProgramRepository programRepository;
    @InjectMocks
    private ProgramServiceImpl programService;

    private final ProgramDO PROGRAM_DO_1 = new ProgramDO(1L, "Master Informatik", Collections.emptyList());
    private final ProgramDO PROGRAM_DO_2 = new ProgramDO(2L, "Informatik", Collections.emptyList());

    @Test
    void testGetAll(){
        when(programRepository.findAll()).thenReturn(List.of(PROGRAM_DO_1, PROGRAM_DO_2));
        List<ProgramDO> expected = List.of(PROGRAM_DO_1, PROGRAM_DO_2);
        List<ProgramTO> programTOs = programService.findAll();

        Assertions.assertEquals(expected.size(), programTOs.size());
        Assertions.assertArrayEquals(
                expected.stream().map(ProgramDO::getName).toArray(),
                programTOs.stream().map(ProgramTO::getName).toArray()
        );
    }

    @Test
    void testGetById(){
        when(programRepository.findById(1L)).thenReturn(Optional.of(PROGRAM_DO_1));
        when(programRepository.findById(3L)).thenReturn(Optional.empty());

        Assertions.assertEquals(PROGRAM_DO_1.getId(), programService.findById(1L).getId());
        Assertions.assertThrows(NotFoundException.class, ()-> programService.findById(3L));
    }

    @Test
    void testCreate(){
        when(programRepository.save(any(ProgramDO.class))).thenReturn(PROGRAM_DO_1);
        ProgramTO testTO = ProgramTO.fromDO(PROGRAM_DO_1);
        testTO.setId(null);

        ProgramTO actual = programService.createProgram(testTO);
        Assertions.assertEquals(PROGRAM_DO_1.getId(), actual.getId());
        Assertions.assertEquals(PROGRAM_DO_1.getName(), actual.getName());
    }

    @Test
    void testUpdate(){
        ProgramDO testReturn = new ProgramDO(PROGRAM_DO_1.getId(), PROGRAM_DO_1.getName(), Collections.emptyList());
        when(programRepository.findById(1L)).thenReturn(Optional.of(testReturn));
        when(programRepository.findById(3L)).thenReturn(Optional.empty());
        when(programRepository.save(any(ProgramDO.class))).thenReturn(testReturn);
        ProgramTO test = ProgramTO.fromDO(PROGRAM_DO_2);

        ProgramTO actual = programService.updateProgram(1L, test, false);
        Assertions.assertNotEquals(PROGRAM_DO_1.getName(), actual.getName());
        Assertions.assertEquals(PROGRAM_DO_2.getName(), actual.getName());

        ProgramTO testThrows = ProgramTO.fromDO(PROGRAM_DO_1);
        Assertions.assertThrows(
                NotFoundException.class,
                ()-> programService.updateProgram(3L, testThrows, false)
        );
    }

    @Test
    void testDelete(){
        when(programRepository.findById(1L)).thenReturn(Optional.of(PROGRAM_DO_1), Optional.empty());

        Assertions.assertEquals(PROGRAM_DO_1.getId(), programService.removeProgram(1L).getId());
        Assertions.assertThrows(NotFoundException.class, ()-> programService.removeProgram(1L));
    }

}
