package io.github.onetwostory.recipe.converters.inverse;

import io.github.onetwostory.recipe.commands.IngredientCommand;
import io.github.onetwostory.recipe.model.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientEntityToCommand implements Converter<Ingredient, IngredientCommand> {

    private final UnitOfMeasureEntityToCommand unitOfMeasureConverter;

    public IngredientEntityToCommand(UnitOfMeasureEntityToCommand unitOfMeasureConverter) {
        this.unitOfMeasureConverter = unitOfMeasureConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(Ingredient ingredient) {
        if (ingredient == null)
            return null;

        final IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(ingredient.getId());
        ingredientCommand.setDescription(ingredient.getDescription());
        ingredientCommand.setUnitOfMeasure(unitOfMeasureConverter.convert(ingredient.getUom()));
        ingredientCommand.setAmount(ingredient.getAmount());
        return ingredientCommand;
    }
}
