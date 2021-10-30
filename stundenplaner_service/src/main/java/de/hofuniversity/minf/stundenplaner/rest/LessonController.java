package de.hofuniversity.minf.stundenplaner.rest;


import de.hofuniversity.minf.stundenplaner.service.boundary.LessonService;

import de.hofuniversity.minf.stundenplaner.service.to.LessonTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("lessons")
public class LessonController {

    private final LessonService lessonService;

    @Autowired
    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping
    public ResponseEntity<List<LessonTO>> getAllLesson() {
        return ResponseEntity.ok(lessonService.getAllLessons());
    }


    @PostMapping
    public ResponseEntity<LessonTO> createLesson(
            @RequestBody LessonTO lessonTO) {
        return ResponseEntity.status(201).body(lessonService.createLesson(lessonTO));
    }

    @GetMapping("/{lessonId}")
    public ResponseEntity<LessonTO> findById(
            @PathVariable("lessonId") Long lessonId
    ){
        return ResponseEntity.ok(lessonService.findById(lessonId));
    }

    @PutMapping("/{lessonId}")
    public ResponseEntity<LessonTO> updateLesson(
            @PathVariable("lessonId") Long lessonId,
            @RequestBody LessonTO lessonTO) {
        return ResponseEntity.ok(lessonService.updateLesson(lessonId,lessonTO));
    }

    @DeleteMapping("/{lessonId}")
    public ResponseEntity<LessonTO> deleteLesson(
            @PathVariable("lessonId") Long lessonId) {
        return ResponseEntity.ok(lessonService.removeLesson(lessonId));
    }

}
