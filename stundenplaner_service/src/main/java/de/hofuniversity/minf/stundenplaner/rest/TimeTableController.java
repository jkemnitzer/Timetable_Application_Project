package de.hofuniversity.minf.stundenplaner.rest;

import de.hofuniversity.minf.stundenplaner.service.boundary.TimeTableService;
import de.hofuniversity.minf.stundenplaner.service.to.LessonTO;
import de.hofuniversity.minf.stundenplaner.service.to.TimeTableTO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    @GetMapping(value = "/export", produces = "application/octet-stream")
    public void getExport(HttpServletResponse response) throws IOException {
        this.executeExcelExport(response);
    }

    @GetMapping(value = "/{versionId}/export", produces = "application/octet-stream")
    public void getVersionExport(HttpServletResponse response, @PathVariable("versionId") Long versionId) throws IOException {
        this.executeExcelExport(response, versionId);
    }

    @PostMapping("/import")
    public ResponseEntity<TimeTableTO> importExcelFile(@RequestParam("file") MultipartFile file) throws IOException {
        Workbook workbook = new HSSFWorkbook(file.getInputStream());
        return ResponseEntity.ok(service.importTimeTable(workbook, file.getOriginalFilename()));
    }

    private void executeExcelExport(HttpServletResponse response) throws IOException {
        this.executeExcelExport(response, null);
    }

    private void executeExcelExport(HttpServletResponse response, Long versionId) throws IOException {
        Workbook workbook;
        String headerValue;
        String headerKey = "Content-Disposition";
        if (versionId == null) {
            workbook = service.exportAll();
            headerValue = "attachment; fileName=timetable_all.xls";
        } else {
            workbook = service.exportVersion(versionId);
            headerValue = "attachment; fileName=timetable_version_" + versionId + ".xls";
        }
        response.setHeader(headerKey, headerValue);
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }


}
