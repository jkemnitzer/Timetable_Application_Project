package de.hofuniversity.minf.stundenplaner.rest;

import de.hofuniversity.minf.stundenplaner.service.ProgramService;
import de.hofuniversity.minf.stundenplaner.service.to.ProgramTO;
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

import java.util.List;

@RestController
@RequestMapping("programs")
public class ProgramController {

    private final ProgramService programService;

    @Autowired
    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    @GetMapping
    public ResponseEntity<List<ProgramTO>> getAllPrograms(){
        return ResponseEntity.ok(programService.findAll());
    }

    @PostMapping
    public ResponseEntity<ProgramTO> createProgram(@RequestBody ProgramTO programTo){
        return ResponseEntity.status(201).body(programService.createProgram(programTo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgramTO> getById(@PathVariable("id") Long id){
        return ResponseEntity.ok(programService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProgramTO> updateProgram(@PathVariable("id") Long id, @RequestBody ProgramTO programTo){
        return ResponseEntity.ok(programService.updateProgram(id, programTo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProgramTO> deleteProgram(@PathVariable("id") Long id){
        ProgramTO deleted = programService.removeProgram(id);
        if (deleted != null && id.equals(deleted.getId())){
            return ResponseEntity.ok(deleted);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
