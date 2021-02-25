package io.github.onetwostory.recipe.service;

import io.github.onetwostory.recipe.commands.IngredientCommand;
import io.github.onetwostory.recipe.commands.RecipeCommand;
import io.github.onetwostory.recipe.model.Ingredient;
import io.github.onetwostory.recipe.model.Recipe;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();
    Set<Ingredient> findIngredientsByRecipeId(Long recipeId);
    Recipe findById(Long id);
    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);
    RecipeCommand findCommandById(Long id);
    void deleteById(Long id);
}
