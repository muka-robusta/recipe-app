package io.github.onetwostory.recipe.converters;

import io.github.onetwostory.recipe.commands.IngredientCommand;
import io.github.onetwostory.recipe.model.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientCommandToEntity implements Converter<IngredientCommand, Ingredient> {

    private final UnitOfMeasureCommandToEntity unitOfMeasureCommandToEntity;

    public IngredientCommandToEntity(UnitOfMeasureCommandToEntity commandToEntity) {
        unitOfMeasureCommandToEntity = commandToEntity;
    }

    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommand ingredientCommand) {
        if (ingredientCommand == null)
            return null;

        final Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientCommand.getId());
        ingredient.setDescription(ingredientCommand.getDescription());
        ingredient.setAmount(ingredientCommand.getAmount());
        ingredient.setUom(unitOfMeasureCommandToEntity.convert(ingredientCommand.getUnitOfMeasure()));
        return ingredient;
    }
}
