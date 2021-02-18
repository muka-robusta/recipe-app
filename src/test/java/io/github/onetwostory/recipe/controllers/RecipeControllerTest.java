package io.github.onetwostory.recipe.controllers;

/*

class RecipeControllerTest {
    @Mock
    RecipeService recipeService;

    RecipeController recipeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeController = new RecipeController(recipeService);
    }

    @Test
    public void getRecipeTest() throws Exception {
        final Recipe recipe = new Recipe();
        recipe.setId(1L);

        final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

        when(recipeService.findById(anyLong())).thenReturn(recipe);

        mockMvc.perform(get("/recipe/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"));
    }

}

 */

import io.github.onetwostory.recipe.model.Recipe;
import io.github.onetwostory.recipe.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class RecipeControllerTest {

    @Mock
    RecipeService recipeService;

    RecipeController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new RecipeController(recipeService);
    }

    @Test
    void testGetRecipe() throws Exception {
        final Recipe recipe = new Recipe();
        recipe.setId(1L);

        final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        when(recipeService.findById(anyLong())).thenReturn(recipe);

        mockMvc.perform(get("/recipe/show/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"));

    }
}
