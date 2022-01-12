package de.hofuniversity.minf.stundenplaner.service;

import de.hofuniversity.minf.stundenplaner.common.exception.NotFoundException;
import de.hofuniversity.minf.stundenplaner.persistence.room.RoomRepository;
import de.hofuniversity.minf.stundenplaner.persistence.room.data.FeatureDO;
import de.hofuniversity.minf.stundenplaner.persistence.room.data.RoomDO;
import de.hofuniversity.minf.stundenplaner.service.to.RoomTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;
    @InjectMocks
    private RoomServiceImpl roomService;

    private final RoomDO ROOM_DO_1 = new RoomDO(1L, "123", "TestBuildung1",  new ArrayList<FeatureDO>(),100, "Hof");
    private final RoomDO ROOM_DO_2 = new RoomDO(2L, "456", "TestBuildung2",  new ArrayList<FeatureDO>(),100, "Hof");


    @Test
    void testGetAllRoom(){
        when(roomRepository.findAll()).thenReturn(List.of(ROOM_DO_1, ROOM_DO_2));
        List<RoomDO> expected = List.of(ROOM_DO_1, ROOM_DO_2);
        List<RoomTO> programTOs = roomService.getAllRooms();

        Assertions.assertEquals(expected.size(), programTOs.size());
        Assertions.assertArrayEquals(
                expected.stream().map(RoomDO::getBuilding).toArray(),
                programTOs.stream().map(RoomTO::getBuilding).toArray()
        );
    }

    @Test
    void testFindById(){
        when(roomRepository.findById(1L)).thenReturn(Optional.of(ROOM_DO_1));
        when(roomRepository.findById(3L)).thenReturn(Optional.empty());

        Assertions.assertEquals(ROOM_DO_1.getId(), roomService.findById(1L).getId());
        Assertions.assertThrows(NotFoundException.class, ()-> roomService.findById(3L));
    }

    @Test
    void testCreate(){

        when(roomRepository.save(any(RoomDO.class))).thenReturn(ROOM_DO_1);
        RoomTO testTO = RoomTO.fromDO(ROOM_DO_1);
        testTO.setId(null);

        RoomTO actual = roomService.createRoom(testTO);
        Assertions.assertEquals(ROOM_DO_1.getId(), actual.getId());
        Assertions.assertEquals(ROOM_DO_1.getRoomNumber(), actual.getNumber());
    }

    @Test
    void testUpdate(){
        RoomDO testReturn = new RoomDO(ROOM_DO_1.getId(), ROOM_DO_1.getRoomNumber(), "TestBuildung1",  new ArrayList<FeatureDO>(),100, "Hof");
        when(roomRepository.findById(1L)).thenReturn(Optional.of(testReturn));
        when(roomRepository.findById(3L)).thenReturn(Optional.empty());
        when(roomRepository.save(any(RoomDO.class))).thenReturn(testReturn);
        RoomTO testTO = RoomTO.fromDO(ROOM_DO_2);

        RoomTO actual = roomService.updateRoom(1L, testTO);

        Assertions.assertNotEquals(ROOM_DO_1.getRoomNumber(), actual.getNumber());
        Assertions.assertEquals(ROOM_DO_2.getRoomNumber(), actual.getNumber());

        RoomTO testThrows = RoomTO.fromDO(ROOM_DO_1);
        Assertions.assertThrows(
                NotFoundException.class,
                ()-> roomService.updateRoom(3L, testThrows)
        );
    }

    @Test
    void testDelete(){
        when(roomRepository.findById(1L)).thenReturn(Optional.of(ROOM_DO_1), Optional.empty());

        Assertions.assertEquals(ROOM_DO_1.getId(), roomService.removeRoom(1L).getId());
        Assertions.assertThrows(NotFoundException.class, ()-> roomService.removeRoom(1L));
    }



}
