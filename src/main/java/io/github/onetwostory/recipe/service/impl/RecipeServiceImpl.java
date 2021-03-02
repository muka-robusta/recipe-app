package io.github.onetwostory.recipe.service.impl;

import io.github.onetwostory.recipe.commands.IngredientCommand;
import io.github.onetwostory.recipe.commands.RecipeCommand;
import io.github.onetwostory.recipe.converters.RecipeCommandToEntity;
import io.github.onetwostory.recipe.converters.inverse.RecipeEntityToCommand;
import io.github.onetwostory.recipe.exceptions.NotFoundException;
import io.github.onetwostory.recipe.model.Ingredient;
import io.github.onetwostory.recipe.model.Recipe;
import io.github.onetwostory.recipe.repositories.RecipeRepository;
import io.github.onetwostory.recipe.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToEntity recipeConverter;
    // inverse: db entity -> command
    private final RecipeEntityToCommand recipeConverterInverse;


    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository,
                             RecipeCommandToEntity recipeConverter,
                             RecipeEntityToCommand recipeConverterInverse) {
        this.recipeRepository = recipeRepository;
        this.recipeConverter = recipeConverter;
        this.recipeConverterInverse = recipeConverterInverse;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.info("Fetching all recipes");
        Set<Recipe> recipeSet = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
        return recipeSet;
    }

    @Override
    public Recipe findById(Long id) {
        log.info(String.format("Finding by id -> %s", id));
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);

        if (recipeOptional.isEmpty())
            throw new NotFoundException(String.format("Recipe not found -> %s", id));

        return recipeOptional.get();
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
        Recipe recipeToSave = recipeConverter.convert(recipeCommand);

        final Recipe savedRecipe = recipeRepository.save(recipeToSave);
        log.info(String.format("Saved recipe -> %s - %s", savedRecipe.getId(), savedRecipe.getDescription()));
        return recipeConverterInverse.convert(savedRecipe);
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(Long id) {
        return recipeConverterInverse.convert(findById(id));
    }

    @Override
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }

    @Override
    public Set<Ingredient> findIngredientsByRecipeId(Long recipeId) {
        final Recipe recipe = findById(recipeId);
        return recipe.getIngredients();
    }

}
