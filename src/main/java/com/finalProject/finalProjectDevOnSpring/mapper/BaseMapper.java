package com.finalProject.finalProjectDevOnSpring.mapper;

import org.mapstruct.MapperConfig;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@MapperConfig(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BaseMapper<ENTITY, DTO, CHANGE_DTO> {
    DTO toDto(ENTITY entity);

    ENTITY toEntity(DTO dto);

    ENTITY changeToEntity(CHANGE_DTO dto);

    ENTITY merge(@MappingTarget ENTITY oldEntity, ENTITY newEntity);
}
