package io.github.onetwostory.recipe.controllers;

import io.github.onetwostory.recipe.commands.RecipeCommand;
import io.github.onetwostory.recipe.service.ImageService;
import io.github.onetwostory.recipe.service.RecipeService;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Log4j2
@Controller
public class ImageController {

    private final RecipeService recipeService;
    private final ImageService imageService;

    public ImageController(RecipeService recipeService,
                           ImageService imageService) {
        this.recipeService = recipeService;
        this.imageService = imageService;
    }

    @GetMapping("/recipe/{recipeId}/image_update")
    public String uploadRecipeImage(@PathVariable String recipeId,
                                    Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(recipeId)));
        return "recipe/image_upload_form";
    }

    @PostMapping("recipe/{recipeId}/image")
    public String handleImagePost(@PathVariable String recipeId, @RequestParam("image_file") MultipartFile file) {
        imageService.saveRecipeImage(Long.valueOf(recipeId), file);
        return "redirect:/recipe/show/" + recipeId;
    }

    @GetMapping("recipe/{recipeId}/image")
    public void renderImageFromDB(@PathVariable String recipeId, HttpServletResponse response) throws Exception {
        log.info(String.format("Fetching image of recipe -> %s", recipeId));
        final RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));

        byte[] byteArray = new byte[recipeCommand.getImage().length];

        int i = 0;
        for (Byte wrappedByte : recipeCommand.getImage())
            byteArray[i++] = wrappedByte;

        response.setContentType("image/jpeg");
        InputStream stream = new ByteArrayInputStream(byteArray);
        IOUtils.copy(stream, response.getOutputStream());
    }


}
