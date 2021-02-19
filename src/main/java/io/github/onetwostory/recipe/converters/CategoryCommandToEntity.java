package io.github.onetwostory.recipe.converters;

import io.github.onetwostory.recipe.commands.CategoryCommand;
import io.github.onetwostory.recipe.model.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryCommandToEntity implements Converter<CategoryCommand, Category> {

    @Synchronized
    @Nullable
    @Override
    public Category convert(CategoryCommand categoryCommand) {

        if (categoryCommand == null)
            return null;

        final Category category = new Category();
        category.setId(categoryCommand.getId());
        category.setCategoryName(categoryCommand.getDescription());
        return category;
    }
}
