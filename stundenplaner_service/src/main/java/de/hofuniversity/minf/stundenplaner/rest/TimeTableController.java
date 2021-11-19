package de.hofuniversity.minf.stundenplaner.rest;

import de.hofuniversity.minf.stundenplaner.service.boundary.TimeTableService;
import de.hofuniversity.minf.stundenplaner.service.to.LessonTO;
import de.hofuniversity.minf.stundenplaner.service.to.TimeTableTO;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    public ResponseEntity<List<LessonTO>> getAll(){
        return ResponseEntity.ok(service.findAllLessons());
    }

    @PostMapping
    public ResponseEntity<TimeTableTO> createTimeTable(@RequestBody TimeTableTO timeTableTO) {
        return ResponseEntity.ok(service.createTimeTable(timeTableTO));
    }

    @GetMapping("/{versionId}")
    public ResponseEntity<TimeTableTO> getTimeTableByVersion(@PathVariable("versionId") Long versionId){
        return ResponseEntity.ok(service.findAllLessonsByVersion(versionId));
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
    public ResponseEntity<LessonTO> deleteLessonById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.deleteLesson(id));
    }

    @GetMapping(value = "/export", produces = "application/octet-stream")
    public void getExport(HttpServletResponse response) throws IOException {
        this.executeExcelExport(response);
    }

    @GetMapping(value = "/{versionId}/export", produces = "application/octet-stream")
    public void getVersionExport(HttpServletResponse response, @PathVariable("versionId") Long versionId) throws IOException {
        this.executeExcelExport(response, versionId);
    }

    private void executeExcelExport(HttpServletResponse response) throws IOException {
        this.executeExcelExport(response, null);
    }

    private void executeExcelExport(HttpServletResponse response, Long versionId) throws IOException {
        Workbook workbook;
        if (versionId == null) {
            workbook = service.exportAll();
        } else {
            workbook = service.exportVersion(versionId);
        }
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=timetable_version_" + versionId + ".xlsx";
        response.setHeader(headerKey, headerValue);
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }


}
