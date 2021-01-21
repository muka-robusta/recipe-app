package io.github.onetwostory.recipe.repositories;

import io.github.onetwostory.recipe.model.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

}
