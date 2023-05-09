package com.epam.esm.repository;

import com.epam.esm.entity.Tag;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

public interface TagRepository {
    /**
     * Finds a tag with the given ID.
     *
     * @param id A Long representing the ID of the Tag to be searched for.
     * @return Returns a Tag object if it exists.
     * @throws EmptyResultDataAccessException if a tag with the given ID does not exist.
     * @throws DataAccessException            if there is a problem accessing the database.
     */
    Tag findById(Long id);


    /**
     * Finds a tag object with the given name.
     *
     * @param name A String type variable representing the name of the tag to be searched for.
     * @return Returns a Tag object if it exists.
     * @throws EmptyResultDataAccessException if a tag with the given name does not exist.
     * @throws DataAccessException            if there is a problem accessing the database.
     */
    Tag findByName(String name);


    /**
     * Finds a list of all tags in the system.
     *
     * @return Returns a list of all Tag objects.
     * @throws DataAccessException if there is a problem accessing the database.
     */
    List<Tag> findAll();


    /**
     * Inserts a new tag entity into the system.
     *
     * @param tag             A Tag object representing the entity to be inserted.
     * @param ignoreDuplicate A boolean value indicating whether to ignore duplicate tag entities or not.
     * @return Returns the ID of the newly inserted tag entity.
     * @throws DataIntegrityViolationException if ignoreDuplicate is false and a tag with the given name already exists.
     * @throws EmptyResultDataAccessException  if ignoreDuplicate is true and a tag with the given name already exists.
     * @throws DataAccessException             if there is a problem accessing the database.
     */
    Long insert(Tag tag, boolean ignoreDuplicate);


    /**
     * Updates an existing tag entity in the system.
     *
     * @param tag A Tag object representing the entity to be updated.
     * @throws EmptyResultDataAccessException if a tag with the given ID does not exist.
     * @throws DataAccessException            if there is a problem accessing the database.
     */
    void update(Tag tag);


    /**
     * Deletes a tag with the given ID from the system.
     *
     * @param id A Long representing the ID of the tag to be deleted.
     * @throws EmptyResultDataAccessException if a tag with the given ID does not exist.
     * @throws DataAccessException            if there is a problem accessing the database.
     */
    void delete(Long id);
}
