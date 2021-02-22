package io.github.onetwostory.recipe.converters.inverse;

import io.github.onetwostory.recipe.commands.RecipeCommand;
import io.github.onetwostory.recipe.model.Category;
import io.github.onetwostory.recipe.model.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeEntityToCommand implements Converter<Recipe, RecipeCommand> {

    private final NotesEntityToCommand notesConverter;
    private final IngredientEntityToCommand ingredientConverter;
    private final CategoryEntityToCommand categoryConverter;

    public RecipeEntityToCommand(NotesEntityToCommand notesConverter,
                                 IngredientEntityToCommand ingredientConverter,
                                 CategoryEntityToCommand categoryConverter) {
        this.notesConverter = notesConverter;
        this.ingredientConverter = ingredientConverter;
        this.categoryConverter = categoryConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe recipe) {

        if (recipe == null)
            return null;

        final RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(recipe.getId());
        recipeCommand.setDescription(recipe.getDescription());
        recipeCommand.setCookTime(recipe.getCookTime());
        recipeCommand.setPrepTime(recipe.getPrepareTime());
        recipeCommand.setDirections(recipe.getDirections());
        recipeCommand.setNotes(notesConverter.convert(recipe.getNotes()));
        recipeCommand.setUrl(recipe.getUrl());
        recipeCommand.setServings(recipe.getServings());
        recipeCommand.setDifficulty(recipe.getDifficulty());

        if (recipe.getCategoryList() != null && recipe.getCategoryList().size() > 0)
            recipe.getCategoryList()
                    .forEach(category -> recipeCommand.getCategories()
                            .add(categoryConverter.convert(category)));

        if (recipe.getIngredients() != null && recipe.getIngredients().size() > 0)
            recipe.getIngredients()
                    .forEach(ingredient -> recipeCommand.getIngredients()
                            .add(ingredientConverter.convert(ingredient)));

        return recipeCommand;
    }
}
