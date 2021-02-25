package io.github.onetwostory.recipe.service.impl;

import io.github.onetwostory.recipe.commands.IngredientCommand;
import io.github.onetwostory.recipe.converters.IngredientCommandToEntity;
import io.github.onetwostory.recipe.converters.inverse.IngredientEntityToCommand;
import io.github.onetwostory.recipe.model.Ingredient;
import io.github.onetwostory.recipe.model.Recipe;
import io.github.onetwostory.recipe.repositories.RecipeRepository;
import io.github.onetwostory.recipe.repositories.UnitOfMeasureRepository;
import io.github.onetwostory.recipe.service.IngredientService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
public class IngredientServiceImpl implements IngredientService {

    // Repos
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    // Converters
    private final IngredientEntityToCommand ingredientEntityToCommand;
    private final IngredientCommandToEntity ingredientCommandToEntity;

    public IngredientServiceImpl(RecipeRepository recipeRepository,
                                 IngredientEntityToCommand ingredientEntityToCommand,
                                 IngredientCommandToEntity ingredientCommandToEntity,
                                 UnitOfMeasureRepository unitOfMeasureRepository) {

        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientEntityToCommand = ingredientEntityToCommand;
        this.ingredientCommandToEntity = ingredientCommandToEntity;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        final Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (recipeOptional.isEmpty())
            throw new RuntimeException(String.format("Unable to find recipe by id -> %s", recipeId));

        final Recipe recipe = recipeOptional.get();
        final Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredientEntityToCommand::convert)
                .findFirst();

        if (ingredientCommandOptional.isEmpty())
            throw new RuntimeException(String.format("Unable to find ingredient by id -> %s", ingredientId));

        return ingredientCommandOptional.get();
    }

    @Transactional
    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        final Optional<Recipe> recipeByIdOptional = recipeRepository.findById(ingredientCommand.getRecipeId());

        if (recipeByIdOptional.isEmpty())
            return new IngredientCommand();

        final Recipe recipe = recipeByIdOptional.get();

        final Optional<Ingredient> ingredientOptional = recipe.getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                .findFirst();

        if (ingredientOptional.isEmpty()) {
            // if ingredient doesnt exists -> create
            final Ingredient ingredient = ingredientCommandToEntity.convert(ingredientCommand);
            ingredient.setRecipe(recipe);
            recipe.addIngredient(ingredient);

        } else {
            // updating ingredient
            final Ingredient ingredientFound = ingredientOptional.get();
            ingredientFound.setDescription(ingredientCommand.getDescription());
            ingredientFound.setAmount(ingredientCommand.getAmount());
            ingredientFound.setUom(unitOfMeasureRepository
                    .findById(ingredientCommand.getUnitOfMeasure().getId())
                    .orElseThrow(() -> new RuntimeException("Unit Of Measure is not found")));
        }

        final Recipe savedRecipe = recipeRepository.save(recipe);

        Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients()
                .stream()
                .filter(recipeIngredient -> recipeIngredient.getId().equals(ingredientCommand.getId()))
                .findFirst();

        if (savedIngredientOptional.isEmpty()) {
            // new ingredient doesnt have id -> finding by properties
            savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredient -> recipeIngredient.getDescription().equals(ingredientCommand.getDescription()))
                    .filter(recipeIngredient -> recipeIngredient.getAmount().equals(ingredientCommand.getAmount()))
                    .filter(recipeIngredient -> recipeIngredient.getUom().getId().equals(ingredientCommand.getUnitOfMeasure().getId()))
                    .findFirst();
        }

        return ingredientEntityToCommand.convert(savedIngredientOptional.get());
    }


    @Override
    public void deleteById(Long recipeId, Long ingredientId) {
        log.info(String.format("Deleting in recipe %s by id -> %s", recipeId, ingredientId));

        final Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (recipeOptional.isEmpty())
            return;

        final Recipe recipe = recipeOptional.get();

        final Optional<Ingredient> ingredientToDeleteOptional = recipe.getIngredients()
                .stream()
                .filter(recipeIngredient -> recipeIngredient.getId().equals(ingredientId))
                .findFirst();

        if (ingredientToDeleteOptional.isEmpty())
            return;

        final Ingredient ingredientToDelete = ingredientToDeleteOptional.get();
        ingredientToDelete.setRecipe(null);
        recipe.getIngredients().remove(ingredientToDeleteOptional.get());
        recipeRepository.save(recipe);


    }
}
