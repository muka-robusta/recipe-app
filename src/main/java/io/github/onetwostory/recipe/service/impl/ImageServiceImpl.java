package io.github.onetwostory.recipe.service.impl;

import io.github.onetwostory.recipe.model.Recipe;
import io.github.onetwostory.recipe.repositories.RecipeRepository;
import io.github.onetwostory.recipe.service.ImageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Log4j2
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Transactional
    @Override
    public void saveRecipeImage(Long recipeId, MultipartFile imageFile) {
        try {
            final Recipe recipe = recipeRepository.findById(recipeId).get();

            final Byte[] imageBytesArray = new Byte[imageFile.getBytes().length];

            int i = 0;
            for (byte b : imageFile.getBytes())
                imageBytesArray[i++] = b;

            recipe.setImage(imageBytesArray);

            recipeRepository.save(recipe);
        } catch (IOException e) {
            log.info("Error while reading bytes from image");
            e.printStackTrace();
        }
    }
}
