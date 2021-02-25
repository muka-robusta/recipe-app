package io.github.onetwostory.recipe.controllers;

import io.github.onetwostory.recipe.commands.RecipeCommand;
import io.github.onetwostory.recipe.model.Recipe;
import io.github.onetwostory.recipe.service.RecipeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Log4j2
@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/show/{id}")
    public String showById(@PathVariable String id, Model model) {
        log.info(String.format("Accessing recipe by id -> %s", id));
        model.addAttribute("recipe", recipeService.findById(Long.parseLong(id.trim())));

        return "recipe/show";
    }

    @GetMapping("recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipe_form";
    }

    @GetMapping("recipe/update/{id}")
    public String updateRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));

        return "recipe/recipe_form";
    }

    @PostMapping("/recipe/")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
        final RecipeCommand recipeCommand = recipeService.saveRecipeCommand(command);

        return "redirect:/recipe/show/" + recipeCommand.getId();
    }

    @GetMapping("recipe/delete/{id}")
    public String deleteRecipe(@PathVariable String id) {
        log.info(String.format("Deleting request by id -> %s", id));
        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }

}
