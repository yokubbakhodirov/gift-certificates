package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.DataAccessFailureException;
import com.epam.esm.exception.DataNotFoundException;

import java.util.List;

public interface GiftCertificateService {
    /**
     * Finds a gift certificate with the given ID.
     *
     * @param id A Long representing the ID of the gift certificate to be searched for.
     * @return Returns a GiftCertificateDto object if it exists.
     * @throws DataNotFoundException if a gift certificate with the given ID does not exist.
     * @throws DataAccessFailureException if there is a problem accessing the database.
     */
    GiftCertificateDto findById(Long id);


    /**
     * Finds a list of gift certificates in the system.
     *
     * @param tagName (Optional) A String representing the tag name to filter the gift certificates by.
     *                If null, no filtering by tag will be applied.
     * @param search (Optional) A String representing a part of the name or description of the gift certificates
     *               to search for. If null, no search filtering will be applied.
     * @param sort (Optional) A String representing the type of sorting to be applied to the results. If null,
     *             no sorting will be applied. The value of this parameter should be a string composed of two parts:
     *             a field to sort by (name or date), and a sort direction (asc or desc), separated by an underscore.
     *             e.g. "date_desc" would sort the results by date in descending order.
     * @return Returns a list of GiftCertificateDto objects that match the specified filter and sorting criteria.
     * @throws DataAccessFailureException if there is a problem accessing the database.
     */
    List<GiftCertificateDto> findAll(String tagName, String search, String sort);


    /**
     * Inserts a new gift certificate entity into the system. If tags are passed, they are inserted and
     * associated with gift certificate.
     *
     * @param giftCertificateDto A GiftCertificateDto object representing the entity to be inserted.
     * @return Returns the ID of the newly inserted gift certificate.
     * @throws DataAccessFailureException if there is a problem accessing the database.
     */
    Long insert(GiftCertificateDto giftCertificateDto);


    /**
     * Updates an existing gift certificate entity in the system. If tags are passed, they are inserted and
     * associated with gift certificate.
     *
     * @param giftCertificateDto A GiftCertificateDto object representing the entity to be updated.
     * @throws DataNotFoundException if a gift certificate with the given ID does not exist.
     * @throws DataAccessFailureException if there is a problem accessing the database.
     */
    void update(GiftCertificateDto giftCertificateDto);


    /**
     * Deletes a gift certificate with the given ID from the system.
     *
     * @param id A Long representing the ID of the GiftCertificate to be deleted.
     * @throws DataNotFoundException if a GiftCertificate object with the given ID does not exist.
     * @throws DataAccessFailureException if there is a problem accessing the database.
     */
    void delete(Long id);
}
