package io.github.onetwostory.recipe.converters;

import io.github.onetwostory.recipe.commands.UnitOfMeasureCommand;
import io.github.onetwostory.recipe.model.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureCommandToEntityTest {
    public static final String DESCRIPTION = "description";
    public static final Long LONG_VALUE = 1L;

    UnitOfMeasureCommandToEntity converter;

    @BeforeEach
    void setUp() {
        converter = new UnitOfMeasureCommandToEntity();
    }

    @Test
    void testNullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new UnitOfMeasureCommand()));
    }

    @Test
    void testConvert() {
        final UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(LONG_VALUE);
        unitOfMeasureCommand.setDescription(DESCRIPTION);

        final UnitOfMeasure converted = converter.convert(unitOfMeasureCommand);

        assertNotNull(converted);
        assertEquals(converted.getId(), LONG_VALUE);
        assertEquals(converted.getDescription(), DESCRIPTION);
    }
}