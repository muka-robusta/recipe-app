package io.github.onetwostory.recipe.service.impl;

import io.github.onetwostory.recipe.model.Recipe;
import io.github.onetwostory.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository);
    }

    @Test
    void getRecipeByIdTest() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        final Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        Recipe recipeReturned = recipeService.findById(1L);

        assertNotNull(recipeReturned);
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();

    }

    @Test
    void getRecipes() {

        final Recipe recipe = new Recipe();
        final Recipe recipe2 = new Recipe();
        recipe.setDirections("asdfklhaslkdfhsajklh");
        Set<Recipe> recipesData = new HashSet<>();
        recipesData.add(recipe);
        recipesData.add(recipe2);

        recipeRepository.findAll();
        when(recipeRepository.findAll()).thenReturn(new ArrayList<>(recipesData));

        Set<Recipe> recipes = recipeService.getRecipes();
        assertEquals(recipes.size(), 2);
        verify(recipeRepository, times(2)).findAll();
    }
}