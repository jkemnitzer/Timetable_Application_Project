package de.hofuniversity.minf.stundenplaner.service;

import de.hofuniversity.minf.stundenplaner.common.exception.NotFoundException;
import de.hofuniversity.minf.stundenplaner.persistence.role.RoleRepository;
import de.hofuniversity.minf.stundenplaner.persistence.role.data.RoleDO;
import de.hofuniversity.minf.stundenplaner.service.boundary.RoleService;
import de.hofuniversity.minf.stundenplaner.service.to.RoleTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleTO> findAll() {
        return StreamSupport.stream(roleRepository.findAll().spliterator(), false)
                .map(RoleTO::fromDO)
                .toList();
    }

    @Override
    public RoleTO findById(Long id) {
        Optional<RoleDO> optional = roleRepository.findById(id);
        if (optional.isPresent()) {
            return RoleTO.fromDO(optional.get());
        } else {
            throw new NotFoundException(RoleDO.class, id);
        }
    }
}
