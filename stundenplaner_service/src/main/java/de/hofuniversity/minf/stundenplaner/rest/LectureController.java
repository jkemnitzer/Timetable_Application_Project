package de.hofuniversity.minf.stundenplaner.rest;

import de.hofuniversity.minf.stundenplaner.service.boundary.LectureService;
import de.hofuniversity.minf.stundenplaner.service.to.LectureTO;
import de.hofuniversity.minf.stundenplaner.service.to.LessonTO;
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
    public ResponseEntity<List<LectureTO>> getAllLectures() {
        return ResponseEntity.ok(lectureService.getAllLectures());
    }

    @PostMapping
    public ResponseEntity<LectureTO> createLecture(
            @RequestBody LectureTO lectureTO) {
        return ResponseEntity.status(201).body(lectureService.createLecture(lectureTO));
    }

    @GetMapping("/{lectureId}")
    public ResponseEntity<LectureTO> findById(
            @PathVariable("lectureId") Long lectureId
    ){
        return ResponseEntity.ok(lectureService.findById(lectureId));
    }

    @PutMapping("/{lectureId}")
    public ResponseEntity<LectureTO> updateLecture(
            @PathVariable("lectureId") Long lectureId,
            @RequestBody LectureTO lectureTO) {
        return ResponseEntity.ok(lectureService.updateLecture(lectureId,lectureTO));
    }

    @DeleteMapping("/{lectureId}")
    public ResponseEntity<LectureTO> deleteLecture(
            @PathVariable("lectureId") Long lectureId) {
        return ResponseEntity.ok(lectureService.removeLecture(lectureId));
    }
    @PostMapping("/{lectureId}/{lessonId}")
    public ResponseEntity<LectureTO> addLesson(
            @PathVariable("lectureId") Long lectureId,
            @PathVariable("lessonId") Long lessonId) {
        return ResponseEntity.ok(lectureService.addLesson(lectureId,lessonId));
    }

    @DeleteMapping("/{lectureId}/{lessonId}")
    public ResponseEntity<LessonTO> deleteLesson(
            @PathVariable("lectureId") Long lectureId,
            @PathVariable("lessonId") Long lessonId)
    {
        return ResponseEntity.ok(lectureService.removeLesson(lectureId,lessonId));
    }

}
