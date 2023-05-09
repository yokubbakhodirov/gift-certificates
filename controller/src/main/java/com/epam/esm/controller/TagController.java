package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.BindingResultException;
import com.epam.esm.exception.DataAccessFailureException;
import com.epam.esm.exception.DataNotFoundException;
import com.epam.esm.service.TagService;
import com.epam.esm.utils.BindingResultChecker;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("tags")
public class TagController {
    private final TagService tagService;

    /**
     * Retrieves a tag with the specified ID.
     *
     * @param id the ID of the tag to retrieve
     * @return a ResponseEntity containing the tag with the specified ID and an HTTP status of 200 (OK).
     * @throws DataNotFoundException      if the requested tag does not exist
     * @throws DataAccessFailureException if there is a problem with the database
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<TagDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.findById(id));
    }

    /**
     * Retrieves a list of all tags in the system.
     *
     * @return Returns a ResponseEntity containing a list of Tag objects and an HTTP status of 200 (OK) if successful.
     * @throws DataAccessFailureException if there is a problem accessing the database.
     */
    @GetMapping
    public ResponseEntity<List<TagDto>> getAll() {
        return ResponseEntity.ok(tagService.findAll());
    }


    /**
     * Inserts a new tag into the system.
     *
     * @param tagDto        A TagDto object representing the entity to be inserted.
     * @param bindingResult A BindingResult object that holds the results of the validation and data binding.
     * @param uriBuilder    A UriComponentsBuilder object that helps build the URI for the newly created resource.
     * @return Returns a ResponseEntity object with a 201 (CREATED) status code and a URI pointing
     * to the newly created resource.
     * @throws BindingResultException     if the tag dto fails validation.
     * @throws DataAccessFailureException if there is a problem accessing the database.
     */
    @PostMapping
    public ResponseEntity<String> insert(@RequestBody @Valid TagDto tagDto,
                                         BindingResult bindingResult, UriComponentsBuilder uriBuilder) {
        BindingResultChecker.check(bindingResult);
        Long id = tagService.insert(tagDto);

        URI locationUri = uriBuilder.path("tags/{id}")
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(locationUri).build();
    }


    /**
     * Updates an existing tag entity in the system with the specified ID.
     *
     * @param id                 The ID of the tag to be updated.
     * @param tagDto A tag object representing the entity to be updated.
     * @return Returns a ResponseEntity with no content and a success status code of 204 if the update is successful.
     * @throws DataNotFoundException      if a tag with the given ID does not exist.
     * @throws DataAccessFailureException if there is a problem accessing the database.
     */
    @PatchMapping(value = "/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid TagDto tagDto,
                                       BindingResult bindingResult) {
        BindingResultChecker.check(bindingResult);
        tagDto.setId(id);

        tagService.update(tagDto);
        return ResponseEntity.noContent().build();
    }

    /**
     * Deletes a tag with the given ID from the system.
     *
     * @param id A Long representing the ID of the tag to be deleted.
     * @return Returns a ResponseEntity with no content and a success status code of 204 if the update is successful.
     * @throws DataNotFoundException      if a tag with the given ID does not exist.
     * @throws DataAccessFailureException if there is a problem accessing the database.
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
