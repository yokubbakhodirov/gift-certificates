package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DataAccessFailureException;
import com.epam.esm.exception.DataNotFoundException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.utils.converter.GiftCertificateConverter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.esm.utils.constant.ExceptionMessage.*;

@Service
@Transactional
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository giftCertificateRepository;
    private final TagRepository tagRepository;
    private final GiftCertificateConverter giftCertificateConverter;
    private final Logger logger = LoggerFactory.getLogger(GiftCertificateServiceImpl.class);

    @Override
    public GiftCertificateDto findById(Long id) {
        try {
            logger.info("Finding gift certificate by ID: {}", id);

            return giftCertificateConverter.toDto(giftCertificateRepository.findById(id));
        } catch (EmptyResultDataAccessException e) {
            String message = String.format(GIFT_CERTIFICATE_NOT_FOUND, id);
            logger.info(message);
            throw new DataNotFoundException(message);
        } catch (DataAccessException e) {
            String message = String.format(GIFT_CERTIFICATE_NOT_FOUND, id);
            logger.info(message + e.getMessage());
            throw new DataAccessFailureException(message, e);
        }
    }

    @Override
    public List<GiftCertificateDto> findAll(String tagName, String search, String sort) {
        try {
            logger.info("Finding all gift certificates");
            return giftCertificateRepository.findAll(tagName, search, sort).stream()
                    .map(giftCertificateConverter::toDto)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            logger.error(GIFT_CERTIFICATE_FIND_ALL_FAILED + e.getMessage());
            throw new DataAccessFailureException(GIFT_CERTIFICATE_FIND_ALL_FAILED, e);
        }
    }

    @Override
    public Long insert(GiftCertificateDto giftCertificateDto) {
        try {
            logger.info("Inserting gift certificate: {}", giftCertificateDto);
            GiftCertificate giftCertificate = giftCertificateConverter.toEntity(giftCertificateDto);
            Long id = giftCertificateRepository.insert(giftCertificate);
            giftCertificate.setId(id);

            insertTagsForGiftCertificate(giftCertificate.getTags());
            giftCertificateRepository.insertGiftCertificateTagRelation(giftCertificate);
            return id;
        } catch (DataAccessException e) {
            String message = String.format(GIFT_CERTIFICATE_INSERT_FAILED, giftCertificateDto);
            logger.error(message + e.getMessage());
            throw new DataAccessFailureException(message, e);
        }
    }

    @Override
    public void update(GiftCertificateDto giftCertificateDto) {
        Long id = giftCertificateDto.getId();
        try {
            logger.info("Updating gift certificate: {}", giftCertificateDto);
            GiftCertificate giftCertificateForUpdate = giftCertificateRepository.findById(id);
            GiftCertificate giftCertificate = giftCertificateConverter.toEntity(giftCertificateDto);

            Optional.ofNullable(giftCertificate.getName()).ifPresent(giftCertificateForUpdate::setName);
            Optional.ofNullable(giftCertificate.getDescription()).ifPresent(giftCertificateForUpdate::setDescription);
            Optional.ofNullable(giftCertificate.getDuration()).ifPresent(giftCertificateForUpdate::setDuration);
            Optional.ofNullable(giftCertificate.getPrice()).ifPresent(giftCertificateForUpdate::setPrice);

            giftCertificateRepository.update(giftCertificateForUpdate);
            insertTagsForGiftCertificate(giftCertificate.getTags());

            giftCertificate.setId(giftCertificateForUpdate.getId());
            giftCertificateRepository.insertGiftCertificateTagRelation(giftCertificate);
        } catch (EmptyResultDataAccessException e) {
            String message = String.format(GIFT_CERTIFICATE_NOT_FOUND, id);
            logger.info(message);
            throw new DataNotFoundException(message);
        } catch (DataAccessException e) {
            String message = String.format(GIFT_CERTIFICATE_UPDATE_FAILED, giftCertificateDto);
            logger.error(message + e.getMessage());
            throw new DataAccessFailureException(message, e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            logger.info("Deleting gift certificate with ID: {}", id);
            giftCertificateRepository.findById(id);
            giftCertificateRepository.delete(id);
        } catch (EmptyResultDataAccessException e) {
            String message = String.format(GIFT_CERTIFICATE_NOT_FOUND, id);
            logger.info(message, e.getMessage());
            throw new DataNotFoundException(message);
        } catch (DataAccessException e) {
            String message = String.format(GIFT_CERTIFICATE_DELETE_FAILED, id);
            logger.error(message + e.getMessage());
            throw new DataAccessFailureException(message, e);
        }
    }

    public void insertTagsForGiftCertificate(Set<Tag> tags) {
        if (tags == null || tags.size() == 0) {
            return;
        }

        logger.info("Inserting tags for gift certificate");
        for (Tag tag : tags) {
                try {
                    Long id = tagRepository.insert(tag, true);
                    tag.setId(id);
                } catch (EmptyResultDataAccessException e) {
                    Optional.ofNullable(tagRepository.findByName(tag.getName())).ifPresent(tag1 -> tag.setId(tag1.getId()));
                    String message = String.format(TAG_INSERT_DUPLICATE_ENTRY, tag);
                    logger.info(message);
                } catch (DataAccessException e) {
                    String message = String.format(GIFT_CERTIFICATE_INSERT_TAG_FAILED, tag);
                    logger.error(message + e.getMessage());
                    throw new DataAccessFailureException(message, e);
                }
        }
    }

}
