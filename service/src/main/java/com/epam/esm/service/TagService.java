package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.DataAccessFailureException;
import com.epam.esm.exception.DataNotFoundException;
import com.epam.esm.exception.DuplicateEntryException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

public interface TagService {
    /**
     * Finds a tag with the given ID.
     *
     * @param id A Long representing the ID of the Tag to be searched for.
     * @return Returns a Tag object if it exists.
     * @throws DataNotFoundException if a tag with the given ID does not exist.
     * @throws DataAccessFailureException if there is a problem accessing the database.
     */
    TagDto findById(Long id);


    /**
     * Finds a list of all tags in the system.
     *
     * @return Returns a list of Tag objects.
     * @throws DataAccessFailureException if there is a problem accessing the database.
     */
    List<TagDto> findAll();


    /**
     * Inserts a new tag entity into the system.
     *
     * @param tagDto A TagDto object representing the entity to be inserted.
     * @return Returns the ID of the newly inserted tag entity.
     * @throws DuplicateEntryException if a tag with the given name already exists.
     * @throws DataAccessFailureException if there is a problem accessing the database.
     */
    Long insert(TagDto tagDto);


    /**
     * Updates an existing tag entity in the system.
     *
     * @param tagDto A TagDto object representing the entity to be updated.
     * @throws EmptyResultDataAccessException if a tag with the given ID does not exist.
     * @throws DataAccessFailureException if there is a problem accessing the database.
     */
    void update(TagDto tagDto);


    /**
     * Deletes a tag with the given ID from the system.
     *
     * @param id A Long representing the ID of the tag to be deleted.
     * @throws DataNotFoundException if a tag with the given ID does not exist.
     * @throws DataAccessFailureException if there is a problem accessing the database.
     */
    void delete(Long id);
}
