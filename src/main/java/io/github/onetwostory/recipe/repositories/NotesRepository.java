package io.github.onetwostory.recipe.repositories;

import io.github.onetwostory.recipe.model.Notes;
import org.springframework.data.repository.CrudRepository;

public interface NotesRepository extends CrudRepository<Notes, Long> {
}
