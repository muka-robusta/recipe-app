package io.github.onetwostory.recipe.converters;

import io.github.onetwostory.recipe.commands.RecipeCommand;
import io.github.onetwostory.recipe.model.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeCommandToEntity implements Converter<RecipeCommand, Recipe> {

    private final CategoryCommandToEntity categoryCommandToEntity;
    private final IngredientCommandToEntity ingredientCommandToEntity;
    private final NotesCommandToEntity notesCommandToEntity;

    public RecipeCommandToEntity(CategoryCommandToEntity categoryCommandToEntity,
                                 IngredientCommandToEntity ingredientCommandToEntity,
                                 NotesCommandToEntity notesCommandToEntity) {
        this.categoryCommandToEntity = categoryCommandToEntity;
        this.ingredientCommandToEntity = ingredientCommandToEntity;
        this.notesCommandToEntity = notesCommandToEntity;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand recipeCommand) {

        if (recipeCommand == null)
            return null;

        final Recipe recipe = new Recipe();
        recipe.setId(recipeCommand.getId());
        recipe.setDirections(recipeCommand.getDirections());
        recipe.setDescription(recipeCommand.getDescription());
        recipe.setDifficulty(recipeCommand.getDifficulty());
        recipe.setCookTime(recipeCommand.getCookTime());
        recipe.setPrepareTime(recipeCommand.getPrepTime());
        recipe.setServings(recipeCommand.getServings());
        recipe.setUrl(recipeCommand.getUrl());
        recipe.setNotes(notesCommandToEntity.convert(recipeCommand.getNotes()));
        recipe.setSource(recipeCommand.getSource());

        if (recipeCommand.getCategories() != null && recipeCommand.getCategories().size() > 0)
            recipeCommand.getCategories().stream()
                    .forEach(categoryCommand -> recipe.getCategoryList()
                            .add(categoryCommandToEntity.convert(categoryCommand)));

        if (recipeCommand.getIngredients() != null && recipeCommand.getIngredients().size() > 0)
            recipeCommand.getIngredients().stream()
                    .forEach(ingredientCommand -> recipe.getIngredients()
                            .add(ingredientCommandToEntity.convert(ingredientCommand)));


        return recipe;
    }
}
