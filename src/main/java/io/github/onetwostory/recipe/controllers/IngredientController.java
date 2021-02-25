package io.github.onetwostory.recipe.controllers;

import io.github.onetwostory.recipe.commands.IngredientCommand;
import io.github.onetwostory.recipe.commands.RecipeCommand;
import io.github.onetwostory.recipe.commands.UnitOfMeasureCommand;
import io.github.onetwostory.recipe.service.IngredientService;
import io.github.onetwostory.recipe.service.RecipeService;
import io.github.onetwostory.recipe.service.UOMService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UOMService uomService;

    public IngredientController(RecipeService recipeService,
                                IngredientService ingredientService,
                                UOMService uomService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.uomService = uomService;
    }

    @GetMapping("recipe/{recipeId}/ingredients")
    public String listIngredients(@PathVariable String recipeId, Model model) {

        final Long recipeIdLong = Long.valueOf(recipeId);
        model.addAttribute("recipe", recipeService.findCommandById(recipeIdLong));

        return "recipe/ingredient/list";
    }

    @GetMapping("/recipe/{recipeId}/ingredients/{id}/show")
    public String showIngredient(@PathVariable String recipeId,
                                 @PathVariable String id,
                                 Model model) {
        Long recipeIdLong = Long.valueOf(recipeId);
        Long ingredientIdLong = Long.valueOf(id);
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeIdLong, ingredientIdLong));

        return "recipe/ingredient/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredients/{id}/update")
    public String editIngredient(@PathVariable String recipeId,
                                  @PathVariable String id,
                                  Model model) {

        Long recipeIdLong = Long.valueOf(recipeId);
        Long ingredientId = Long.valueOf(id);

        model.addAttribute("uomList", uomService.listAllUomCommands());
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeIdLong, ingredientId));

        return "recipe/ingredient/ingredient_form";
    }

    @GetMapping("/recipe/{recipeId}/ingredients/create")
    public String addIngredient(@PathVariable String recipeId,
                                Model model) {

        Long recipeIdLong = Long.valueOf(recipeId);
        final RecipeCommand recipeCommand = recipeService.findCommandById(recipeIdLong);
        // TODO: raise exception if null


        final IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(recipeIdLong);
        ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());

        model.addAttribute("uomList", uomService.listAllUomCommands());
        model.addAttribute("ingredient", ingredientCommand);

        return "recipe/ingredient/ingredient_form";
    }

    @PostMapping("recipe/{recipeId}/ingredients")
    public String createOrUpdateIngredient(@ModelAttribute IngredientCommand command) {

        final IngredientCommand ingredientCommand = ingredientService.saveIngredientCommand(command);
        log.info(String.format("Saving or updating ingredient -> %s - %s", command.getId(), command.getDescription()));

        return "redirect:/recipe/" + ingredientCommand.getRecipeId() + "/ingredients/" + ingredientCommand.getId() + "/show";
    }

    @GetMapping("recipe/{recipeId}/ingredients/{ingredientId}/delete")
    public String deleteIngredientFromRecipe(@PathVariable String recipeId,
                                             @PathVariable String ingredientId) {
        log.info(String.format("Deleting request recipe and ingredient ids -> %s %s", recipeId, ingredientId));
        ingredientService.deleteById(Long.valueOf(recipeId), Long.valueOf(ingredientId));

        return "redirect:/recipe/" + recipeId + "/ingredients";
    }

}
