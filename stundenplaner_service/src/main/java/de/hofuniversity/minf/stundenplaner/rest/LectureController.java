package de.hofuniversity.minf.stundenplaner.rest;

import de.hofuniversity.minf.stundenplaner.common.security.RequiredPermission;
import de.hofuniversity.minf.stundenplaner.persistence.permission.data.PermissionTypeEnum;
import de.hofuniversity.minf.stundenplaner.service.boundary.LectureService;
import de.hofuniversity.minf.stundenplaner.service.to.LectureTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("lectures")
public class LectureController {

    private final LectureService lectureService;

    @Autowired
    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @GetMapping
    @RequiredPermission(PermissionTypeEnum.CAN_READ_LECTURES)
    public ResponseEntity<List<LectureTO>> getAllLectures() {
        return ResponseEntity.ok(lectureService.getAllLectures());
    }

    @PostMapping
    @RequiredPermission(PermissionTypeEnum.CAN_CREATE_LECTURES)
    public ResponseEntity<LectureTO> createLecture(
            @RequestBody LectureTO lectureTO) {
        return ResponseEntity.status(201).body(lectureService.createLecture(lectureTO));
    }

    @GetMapping("/{lectureId}")
    @RequiredPermission(PermissionTypeEnum.CAN_READ_LECTURES)
    public ResponseEntity<LectureTO> findById(
            @PathVariable("lectureId") Long lectureId
    ) {
        return ResponseEntity.ok(lectureService.findById(lectureId));
    }

    @PutMapping("/{lectureId}")
    @RequiredPermission(PermissionTypeEnum.CAN_UPDATE_LECTURES)
    public ResponseEntity<LectureTO> updateLecture(
            @PathVariable("lectureId") Long lectureId,
            @RequestBody LectureTO lectureTO,
            @RequestParam(value = "checkLessons", defaultValue = "true") Boolean checkLessons) {
        return ResponseEntity.ok(lectureService.updateLecture(lectureId, lectureTO, checkLessons));
    }

    @DeleteMapping("/{lectureId}")
    @RequiredPermission(PermissionTypeEnum.CAN_DELETE_LECTURES)
    public ResponseEntity<LectureTO> deleteLecture(
            @PathVariable("lectureId") Long lectureId) {
        return ResponseEntity.ok(lectureService.removeLecture(lectureId));
    }

    @PostMapping("/{lectureId}/{lessonId}")
    @RequiredPermission(PermissionTypeEnum.CAN_UPDATE_LECTURES)
    public ResponseEntity<LectureTO> addLesson(
            @PathVariable("lectureId") Long lectureId,
            @PathVariable("lessonId") Long lessonId) {
        return ResponseEntity.ok(lectureService.addLesson(lectureId, lessonId));
    }

}
