package io.github.onetwostory.recipe.repositories;

import io.github.onetwostory.recipe.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
