package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.BindingResultException;
import com.epam.esm.exception.DataAccessFailureException;
import com.epam.esm.exception.DataNotFoundException;
import com.epam.esm.service.GiftCertificateService;
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
@RequestMapping("gift-certificates")
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;

    /**
     * Retrieves a gift certificate with the specified ID.
     *
     * @param id the ID of the gift certificate to retrieve
     * @return a ResponseEntity containing the gift certificate with the specified ID and an HTTP status of 200 (OK).
     * @throws DataNotFoundException      if the requested gift certificate does not exist
     * @throws DataAccessFailureException if there is a problem with the database
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<GiftCertificateDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok().body(giftCertificateService.findById(id));
    }


    /**
     * Retrieves a list of gift certificates from the system that match the specified search and filter criteria.
     *
     * @param tag    (Optional) A String representing the tag name to filter the gift certificates by.
     *               If null, no filtering by tag will be applied.
     * @param search (Optional) A String representing a part of the name or description of the gift certificates
     *               to search for. If null, no search filtering will be applied.
     * @param sort   (Optional) A String representing the type of sorting to be applied to the results. If null,
     *               no sorting will be applied. The value of this parameter should be a string composed of two parts:
     *               a field to sort by (name or date), and a sort direction (asc or desc), separated by an underscore.
     *               e.g. "date_desc" would sort the results by date in descending order.
     * @return Returns a ResponseEntity object containing a list of GiftCertificateDto objects that match the specified
     * search and filter criteria. The HTTP status code of the response will be 200 (OK) if successful.
     * @throws DataAccessFailureException if there is a problem accessing the database.
     */
    @GetMapping
    public ResponseEntity<List<GiftCertificateDto>> getAll(@RequestParam(name = "tag", required = false) String tag,
                                                           @RequestParam(name = "search", required = false) String search,
                                                           @RequestParam(name = "sort", required = false) String sort) {
        return ResponseEntity.ok().body(giftCertificateService.findAll(tag, search, sort));
    }


    /**
     * Inserts a new gift certificate into the system.
     * If tags are passed, they are inserted and associated with the gift certificate.
     *
     * @param giftCertificateDto A GiftCertificateDto object representing the entity to be inserted.
     * @param bindingResult      A BindingResult object that holds the results of the validation and data binding.
     * @param uriBuilder         A UriComponentsBuilder object that helps build the URI for the newly created resource.
     * @return Returns a ResponseEntity object with a 201 (CREATED) status code and a URI pointing to the newly created resource.
     * @throws BindingResultException     if the gift certificate DTO fails validation.
     * @throws DataAccessFailureException if there is a problem accessing the database.
     */
    @PostMapping
    public ResponseEntity<String> insert(@RequestBody @Valid GiftCertificateDto giftCertificateDto,
                                         BindingResult bindingResult, UriComponentsBuilder uriBuilder) {
        BindingResultChecker.check(bindingResult);
        Long id = giftCertificateService.insert(giftCertificateDto);

        URI locationUri = uriBuilder.path("gift-certificates/{id}")
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(locationUri).build();
    }


    /**
     * Updates an existing gift certificate entity in the system with the specified ID.
     * If tags are passed, they are inserted and associated with the gift certificate.
     *
     * @param id                 The ID of the gift certificate to be updated.
     * @param giftCertificateDto A GiftCertificateDto object representing the entity to be updated.
     * @return Returns a ResponseEntity with no content and a success status code of 204 if the update is successful.
     * @throws DataNotFoundException      if a gift certificate with the given ID does not exist.
     * @throws DataAccessFailureException if there is a problem accessing the database.
     */
    @PatchMapping(value = "/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody GiftCertificateDto giftCertificateDto,
                                       BindingResult bindingResult) {
        BindingResultChecker.check(bindingResult);
        giftCertificateDto.setId(id);
        giftCertificateService.update(giftCertificateDto);
        return ResponseEntity.noContent().build();
    }


    /**
     * Deletes a gift certificate with the given ID from the system.
     *
     * @param id A Long representing the ID of the gift certificate to be deleted.
     * @return Returns a ResponseEntity with no content and a success status code of 204 if the update is successful.
     * @throws DataNotFoundException      if a gift certificate with the given ID does not exist.
     * @throws DataAccessFailureException if there is a problem accessing the database.
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        giftCertificateService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
