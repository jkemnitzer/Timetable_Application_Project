package de.hofuniversity.minf.stundenplaner.service;

import de.hofuniversity.minf.stundenplaner.persistence.program.ProgramRepository;
import de.hofuniversity.minf.stundenplaner.service.to.ProgramTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProgramServiceImpl implements ProgramService{

    private final ProgramRepository programRepository;

    @Autowired
    public ProgramServiceImpl(ProgramRepository programRepository) {
        this.programRepository = programRepository;
    }

    @Override
    public List<ProgramTO> findAll() {
        return StreamSupport.stream(programRepository.findAll().spliterator(), false)
                .map(programDO -> new ProgramTO())
                .collect(Collectors.toList());
    }

    @Override
    public ProgramTO findById(Long id) {
        return null;
    }

    @Override
    public ProgramTO findByName(String name) {
        return null;
    }
}
