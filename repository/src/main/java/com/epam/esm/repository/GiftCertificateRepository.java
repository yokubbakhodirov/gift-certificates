package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

public interface GiftCertificateRepository {
    /**
     * Finds a gift certificate with the given ID.
     *
     * @param id A Long representing the ID of the gift certificate to be searched for.
     * @return Returns a GiftCertificate object if it exists.
     * @throws EmptyResultDataAccessException if a gift certificate object with the given ID does not exist.
     * @throws DataAccessException            if there is a problem accessing the database.
     */
    GiftCertificate findById(Long id);


    /**
     * Finds a list of gift certificate objects in the system.
     *
     * @param tagName (Optional) A String representing the tag name to filter the gift certificates by.
     *                If null, no filtering by tag will be applied.
     * @param search  (Optional) A String representing a part of the name or description of the gift certificates
     *                to search for. If null, no search filtering will be applied.
     * @param sort    (Optional) A String representing the type of sorting to be applied to the results. If null,
     *                no sorting will be applied. The value of this parameter should be a string composed of two parts:
     *                a field to sort by (name or date), and a sort direction (asc or desc), separated by an underscore.
     *                e.g. "date_desc" would sort the results by date in descending order.
     * @return Returns a List of GiftCertificate objects that match the specified filter and sorting criteria.
     * @throws DataAccessException if there is a problem accessing the database.
     */
    List<GiftCertificate> findAll(String tagName, String search, String sort);


    /**
     * Inserts a new gift certificate entity into the system.
     *
     * @param giftCertificate A GiftCertificate object representing the entity to be inserted.
     * @return Returns the ID of the newly inserted GiftCertificate object.
     * @throws DataAccessException if there is a problem accessing the database.
     */
    Long insert(GiftCertificate giftCertificate);


    /**
     * Updates an existing gift certificate entity in the system.
     *
     * @param giftCertificate A GiftCertificate object representing the entity to be updated.
     * @throws EmptyResultDataAccessException if a gift certificate object with the given ID does not exist.
     * @throws DataAccessException            if there is a problem accessing the database.
     */
    void update(GiftCertificate giftCertificate);


    /**
     * Deletes a gift certificate with the given ID from the system.
     *
     * @param id A Long representing the ID of the GiftCertificate to be deleted.
     * @throws EmptyResultDataAccessException if a gift certificate with the given ID does not exist.
     * @throws DataAccessException            if there is a problem accessing the database.
     */
    void delete(Long id);


    /**
     * Inserts relations between an existing gift certificate and its associated tags in the system.
     *
     * @param giftCertificate A GiftCertificate object representing the entity for which the relations are to be inserted.
     *                        This object should contain the set of Tag objects associated with the GiftCertificate.
     * @throws DataAccessException if there is a problem accessing the database.
     */
    void insertGiftCertificateTagRelation(GiftCertificate giftCertificate);
}
