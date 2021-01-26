package io.github.onetwostory.recipe.service;

import io.github.onetwostory.recipe.model.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();
}
