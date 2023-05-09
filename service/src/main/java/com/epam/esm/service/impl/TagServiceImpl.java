package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DataAccessFailureException;
import com.epam.esm.exception.DataNotFoundException;
import com.epam.esm.exception.DuplicateEntryException;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.utils.converter.TagConverter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.utils.constant.ExceptionMessage.*;

@Service
@Transactional
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final TagConverter tagConverter;
    private final Logger logger = LoggerFactory.getLogger(TagServiceImpl.class);

    @Override
    public TagDto findById(Long id) {
        try {
            logger.info("Finding tag by ID: {}", id);
            Tag tag = tagRepository.findById(id);

            return tagConverter.toDto(tag);
        } catch (EmptyResultDataAccessException e) {
            String message = String.format(TAG_NOT_FOUND, id);
            logger.info(message);
            throw new DataNotFoundException(message);
        } catch (DataAccessException e) {
            String message = String.format(TAG_NOT_FOUND, id);
            logger.error(message +  e.getMessage());
            throw new DataAccessFailureException(message, e);
        }
    }

    @Override
    public List<TagDto> findAll() {
        try {
            logger.info("Finding all tags");
            return tagRepository.findAll().stream()
                    .map(tagConverter::toDto)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            logger.error(TAG_FIND_ALL_FAILED + e.getMessage());
            throw new DataAccessFailureException(TAG_FIND_ALL_FAILED, e);
        }
    }

    @Override
    public Long insert(TagDto tagDto) {
        try {
            logger.info("Inserting tag: {}", tagDto);
            return tagRepository.insert(tagConverter.toEntity(tagDto), false);
        } catch (DataIntegrityViolationException e) {
            String message = String.format(TAG_INSERT_DUPLICATE_ENTRY, tagDto);
            logger.info(message);
            throw new DuplicateEntryException(message);
        } catch (DataAccessException e) {
            String message = String.format(TAG_INSERT_FAILED, tagDto);
            logger.error(message +  e.getMessage());
            throw new DataAccessFailureException(message, e);
        }
    }

    @Override
    public void update(TagDto tagDto) {
        long id = tagDto.getId();
        try {
            logger.info("Updating tag: {}", tagDto);
            tagRepository.findById(id);
            Tag tag = tagConverter.toEntity(tagDto);
            tagRepository.update(tag);
        } catch (EmptyResultDataAccessException e) {
            String message = String.format(TAG_NOT_FOUND, id);
            logger.info(message);
            throw new DataNotFoundException(message);
        } catch (DataAccessException e) {
            String message = String.format(TAG_UPDATE_FAILED, tagDto);
            logger.error(message +  e.getMessage());
            throw new DataAccessFailureException(message, e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            logger.info("Deleting tag with ID: {}", id);
            tagRepository.findById(id);
            tagRepository.delete(id);
        } catch (EmptyResultDataAccessException e) {
            String message = String.format(TAG_NOT_FOUND, id);
            logger.info(message);
            throw new DataNotFoundException(message);
        } catch (DataAccessException e) {
            String message = String.format(TAG_DELETE_FAILED, id);
            logger.error(message +  e.getMessage());
            throw new DataAccessFailureException(message, e);
        }
    }
}
