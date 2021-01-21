package io.github.onetwostory.recipe.Services;

import io.github.onetwostory.recipe.model.Recipe;
import io.github.onetwostory.recipe.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    public void addJhonDoeRecipe() {

        Recipe recipeByJhonDoe = new Recipe();
        recipeByJhonDoe.setDescription("Vermishel");
        recipeByJhonDoe.setCookTime(15);
        recipeByJhonDoe.setPrepareTime(3);

        Recipe savedRecipeByJhonDoe = recipeRepository.save(recipeByJhonDoe);

    }
}
