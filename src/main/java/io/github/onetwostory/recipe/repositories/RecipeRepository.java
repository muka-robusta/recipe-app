package io.github.onetwostory.recipe.repositories;

import io.github.onetwostory.recipe.model.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {

    List<Recipe> findAll();
    Recipe save(Recipe recipe);

}
