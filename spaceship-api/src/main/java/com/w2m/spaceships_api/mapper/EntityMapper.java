package com.w2m.spaceships_api.mapper;


import java.util.List;
import java.util.stream.Collectors;

public interface EntityMapper<E, D> {

    D toDto(E entity);

    E toEntity(D dto);

    default List<D> toDtos(List<E> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

    default List<E> toEntities(List<D> dtos) {
        return dtos.stream().map(this::toEntity).collect(Collectors.toList());
    }
}