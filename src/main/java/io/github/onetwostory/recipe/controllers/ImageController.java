package io.github.onetwostory.recipe.controllers;

import io.github.onetwostory.recipe.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ImageController {

    private final RecipeService recipeService;

    public ImageController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipes/image")
    public String uploadRecipeImage() {

        return "recipe/image_upload_form";
    }

}
