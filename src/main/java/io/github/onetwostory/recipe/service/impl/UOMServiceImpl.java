package io.github.onetwostory.recipe.service.impl;

import io.github.onetwostory.recipe.commands.UnitOfMeasureCommand;
import io.github.onetwostory.recipe.converters.inverse.UnitOfMeasureEntityToCommand;
import io.github.onetwostory.recipe.model.UnitOfMeasure;
import io.github.onetwostory.recipe.repositories.UnitOfMeasureRepository;
import io.github.onetwostory.recipe.service.UOMService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UOMServiceImpl implements UOMService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureEntityToCommand converter;

    public UOMServiceImpl(UnitOfMeasureRepository repo,
                          UnitOfMeasureEntityToCommand converter) {
        unitOfMeasureRepository = repo;
        this.converter = converter;
    }

    @Override
    public Set<UnitOfMeasure> findAll() {
        Set<UnitOfMeasure> unitOfMeasures = new HashSet<>();
        unitOfMeasureRepository.findAll()
                .iterator()
                .forEachRemaining(unitOfMeasures::add);
        return unitOfMeasures;
    }

    @Override
    public Set<UnitOfMeasureCommand> listAllUomCommands() {
        return StreamSupport.stream(unitOfMeasureRepository.findAll().spliterator(), false)
                .map(converter::convert)
                .collect(Collectors.toSet());

    }

}
