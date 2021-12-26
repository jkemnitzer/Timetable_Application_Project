package de.hofuniversity.minf.stundenplaner.rest;

import de.hofuniversity.minf.stundenplaner.service.boundary.TimeTableService;
import de.hofuniversity.minf.stundenplaner.service.to.LessonTO;
import de.hofuniversity.minf.stundenplaner.service.to.TimeTableTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("timetable")
public class TimeTableController {

    private final TimeTableService service;

    @Autowired
    public TimeTableController(TimeTableService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<LessonTO>> getAll(
            @RequestParam(value = "program", required = false) Long programId,
            @RequestParam(value = "semester", required = false) Long semesterId,
            @RequestParam(value = "weekday", required = false) Integer weekdayNr,
            @RequestParam(value = "version", required = false) Long versionId,
            @RequestParam(value = "lecturer", required = false) Long lecturerId,
            @RequestParam(value = "start", required = false) String start,
            @RequestParam(value = "end", required = false) String end
    ) {
        LocalTime startTime = (start != null) ? LocalTime.parse(start) : null;
        LocalTime endTime = (end != null) ? LocalTime.parse(end) : null;
        return ResponseEntity.ok(service.findAllLessons(programId, semesterId, weekdayNr, versionId, lecturerId, startTime, endTime));
    }

    @PostMapping
    public ResponseEntity<TimeTableTO> createTimeTable(@RequestBody TimeTableTO timeTableTO) {
        return ResponseEntity.ok(service.createTimeTable(timeTableTO));
    }

    @GetMapping("/{versionId}")
    public ResponseEntity<TimeTableTO> getTimeTableByVersion(
            @PathVariable("versionId") Long versionId,
            @RequestParam(value = "program", required = false) Long programId,
            @RequestParam(value = "semester", required = false) Long semesterId,
            @RequestParam(value = "weekday", required = false) Integer weekdayNr,
            @RequestParam(value = "lecturer", required = false) Long lecturerId,
            @RequestParam(value = "start", required = false) String start,
            @RequestParam(value = "end", required = false) String end
    ) {
        LocalTime startTime = (start != null) ? LocalTime.parse(start) : null;
        LocalTime endTime = (end != null) ? LocalTime.parse(end) : null;
        return ResponseEntity.ok(service.findAllLessonsByVersion(programId, semesterId, weekdayNr, versionId, lecturerId, startTime, endTime));
    }

    @DeleteMapping("/{versionId}")
    public ResponseEntity<TimeTableTO> deleteTimeTableByVersion(@PathVariable("versionId") Long versionId){
        return ResponseEntity.ok(service.deleteTimeTable(versionId));
    }

    @PostMapping("/lesson")
    public ResponseEntity<LessonTO> createLesson(@RequestBody LessonTO lessonTO){
        return ResponseEntity.ok(service.createSingleLesson(lessonTO));
    }

    @GetMapping("/lesson/{id}")
    public ResponseEntity<LessonTO> getLessonById(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.findSingleLesson(id));
    }

    @PutMapping("/lesson/{id}")
    public ResponseEntity<LessonTO> updateLessonById(@PathVariable("id") Long id, @RequestBody LessonTO lessonTO) {
        return ResponseEntity.ok(service.updateLesson(id, lessonTO));
    }

    @DeleteMapping("/lesson/{id}")
    public ResponseEntity<LessonTO> deleteLessonById(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.deleteLesson(id));
    }

}
