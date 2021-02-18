package io.github.onetwostory.recipe.bootstrap;

import io.github.onetwostory.recipe.model.*;
import io.github.onetwostory.recipe.repositories.CategoryRepository;
import io.github.onetwostory.recipe.repositories.RecipeRepository;
import io.github.onetwostory.recipe.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    public final CategoryRepository categoryRepository;
    public final RecipeRepository recipeRepository;
    public final UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeBootstrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    private List<Recipe> getRecipes() {
        List<Recipe> recipes = new ArrayList<>(1);

        // Fetching unit of measures
        List<String> unitOfMeasuresListString = List.of("each", "teaspoon", "tablespoon", "gram", "cup");
        List<UnitOfMeasure> unitOfMeasures = unitOfMeasuresListString
                .stream()
                .map(unitOfMeasureRepository::findByDescription)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        // Fetching categories
        List<String> categoriesListString = List.of("American", "Italian", "Mexican");
        List<Category> categories = categoriesListString
                .stream()
                .map(categoryRepository::findByCategoryName)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        Recipe pizza = new Recipe();

        pizza.setPrepareTime(120);
        pizza.setCookTime(30);
        pizza.setDifficulty(Difficulty.MODERATE);
        pizza.setDirections("for one pizza. One to two mushrooms thinly sliced will cover a pizza.");
        pizza.setDescription("Homemade pizza");
        pizza.setCategoryList(List.of(categories.get(0), categories.get(1)));

        Notes pizzaNotes = new Notes();
        pizzaNotes.setRecipe(pizza);
        pizzaNotes.setRecipeNotes("Pizza dough is a yeasted dough which requires active dry yeast. Make sure the check the expiration date on the yeast package! Yeast that is too old may be dead and won't work.");

        pizza.setNotes(pizzaNotes);


        pizza.setIngredients(new HashSet<Ingredient>());
        pizza.getIngredients().add(new Ingredient( "muka", new BigDecimal(1), pizza, unitOfMeasures.get(0)));
        pizza.getIngredients().add(new Ingredient( "voda", new BigDecimal(1), pizza, unitOfMeasures.get(0)));

        recipes.add(pizza);

        return recipes;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.save(getRecipes().get(0));
        final Optional<Recipe> byId = recipeRepository.findById(1L);
    }
}
