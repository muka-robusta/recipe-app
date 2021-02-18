package io.github.onetwostory.recipe.controllers;

import io.github.onetwostory.recipe.model.Recipe;
import io.github.onetwostory.recipe.service.RecipeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }
    @RequestMapping("/recipe/show/{id}")
    public String showById(@PathVariable String id, Model model) {
        log.info(String.format("Accessing recipe by id -> %s", id));
        model.addAttribute("recipe", recipeService.findById(Long.parseLong(id.trim())));

        return "recipe/show";
    }

}
