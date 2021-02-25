package io.github.onetwostory.recipe.service;

import io.github.onetwostory.recipe.commands.IngredientCommand;
import io.github.onetwostory.recipe.model.Ingredient;
import org.springframework.stereotype.Service;

public interface IngredientService {
    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
    void deleteById(Long recipeId, Long ingredientId);
}
