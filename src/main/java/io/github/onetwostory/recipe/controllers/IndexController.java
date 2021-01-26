package io.github.onetwostory.recipe.controllers;

import io.github.onetwostory.recipe.service.RecipeService;
import io.github.onetwostory.recipe.model.Recipe;
import io.github.onetwostory.recipe.repositories.CategoryRepository;
import io.github.onetwostory.recipe.repositories.RecipeRepository;
import io.github.onetwostory.recipe.repositories.UnitOfMeasureRepository;
import io.github.onetwostory.recipe.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class IndexController {

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "/index.html", "/index"})
    public String getIndexPage(Model model) {

        model.addAttribute("recipes", recipeService.getRecipes());

        return "index";
    }


}
