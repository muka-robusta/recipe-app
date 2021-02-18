package io.github.onetwostory.recipe.repositories;

import io.github.onetwostory.recipe.model.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {

    List<Recipe> findAll();

    Optional<Recipe> findById(Long aLong);

    Recipe save(Recipe recipe);

}
