package com.finalProject.finalProjectDevOnSpring.services;

import com.finalProject.finalProjectDevOnSpring.exception.ApplicationException;
import com.finalProject.finalProjectDevOnSpring.exception.ApplicationNotFoundException;
import com.finalProject.finalProjectDevOnSpring.repository.BaseRepository;
import com.finalProject.finalProjectDevOnSpring.repository.specifications.BaseSpecificationBuilder;
import com.finalProject.finalProjectDevOnSpring.mapper.BaseMapper;
import com.finalProject.finalProjectDevOnSpring.dto.search.BaseSearchCriteria;
import org.springframework.data.domain.Page;

import java.text.MessageFormat;
import java.util.Objects;

public abstract class CommonService<DTO, EDIT_DTO, ENTITY, LONG> {
    protected final BaseRepository<ENTITY, LONG> repository;
    protected final BaseMapper<ENTITY, DTO, EDIT_DTO> mapper;
    private final BaseSpecificationBuilder<ENTITY> specificationBuilder;

    public CommonService(BaseRepository<ENTITY, LONG> repository,
                         BaseMapper<ENTITY, DTO, EDIT_DTO> mapper,
                         BaseSpecificationBuilder<ENTITY> specificationBuilder) {
        this.repository = repository;
        this.mapper = mapper;
        this.specificationBuilder = specificationBuilder;
    }

    public Page<DTO> findAll(BaseSearchCriteria baseSearchCriteria) {
        if (Objects.isNull(specificationBuilder)) {
            return repository.findAll(baseSearchCriteria.pageRequest()).map(mapper::toDto);
        } else {
            return repository.findAll(
                    specificationBuilder.findBy(baseSearchCriteria),
                    baseSearchCriteria.pageRequest()).map(mapper::toDto);
        }
    }

    public DTO findById(LONG id) throws ApplicationNotFoundException {
        try {
            return mapper.toDto(repository.findById(id)
                    .orElseThrow(() -> new ApplicationNotFoundException(
                            MessageFormat.format("Object with ID: {0} not found", id))
                    ));
        } catch (Throwable e) {
            throw new ApplicationNotFoundException(e);
        }
    }

    public DTO saveOrUpdate(LONG id, EDIT_DTO request) throws ApplicationNotFoundException, ApplicationException {
        if (Objects.isNull(id)) {
            return mapper.toDto(repository.save(mapper.changeToEntity(request)));
        } else {
            ENTITY existingEntity = repository.findById(id)
                    .orElseThrow(() -> new ApplicationNotFoundException(
                            MessageFormat.format("Object with ID: {0} not found", id))
                    );
            ENTITY updatedEntity = mapper.merge(existingEntity, mapper.changeToEntity(request));
            return mapper.toDto(repository.save(updatedEntity));
        }
    }

    public void deleteById(LONG id) {
        repository.deleteById(id);
    }
}
