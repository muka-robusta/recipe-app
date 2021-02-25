package io.github.onetwostory.recipe.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    void saveRecipeImage(Long recipeId, MultipartFile imageFile);
}
