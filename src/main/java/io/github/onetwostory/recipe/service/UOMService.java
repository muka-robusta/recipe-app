package io.github.onetwostory.recipe.service;

import io.github.onetwostory.recipe.commands.UnitOfMeasureCommand;
import io.github.onetwostory.recipe.model.UnitOfMeasure;

import java.util.List;
import java.util.Set;

public interface UOMService {
    Set<UnitOfMeasure> findAll();
    Set<UnitOfMeasureCommand> listAllUomCommands();
}
