package de.hofuniversity.minf.stundenplaner.rest;

import de.hofuniversity.minf.stundenplaner.service.ProgramService;
import de.hofuniversity.minf.stundenplaner.service.to.ProgramTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public List<ProgramTO> getAllPrograms(){
        return programService.findAll();
    }

    @GetMapping("/{id}")
    public ProgramTO getById(@PathVariable("id") Long id){
        return programService.findById(id);
    }

}
