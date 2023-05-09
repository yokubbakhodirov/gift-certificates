package com.epam.esm.repository.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.util.mapper.TagRowMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.epam.esm.util.constant.SqlQueries.*;

@Repository
@AllArgsConstructor
public class TagRepositoryImpl implements TagRepository {
    private final JdbcTemplate jdbcTemplate;
    private final TagRowMapper tagRowMapper;
    private final Logger logger = LoggerFactory.getLogger(TagRepositoryImpl.class);

    @Override
    public Tag findById(Long id) {
        logger.debug("Finding tag by ID: {}", id);
        return jdbcTemplate.queryForObject(FIND_TAG_BY_ID, tagRowMapper, id);
    }

    @Override
    public Tag findByName(String name) {
        logger.debug("Finding tag by name: {}", name);
        return jdbcTemplate.queryForObject(FIND_TAG_BY_NAME, tagRowMapper, name);
    }

    @Override
    public List<Tag> findAll() {
        logger.debug("Finding all tags");
        return jdbcTemplate.query(FIND_ALL_TAGS, tagRowMapper);
    }

    @Override
    public Long insert(Tag tag, boolean ignoreDuplicate) {
        logger.debug("Inserting tag: {}", tag);
        if (ignoreDuplicate) {
            return jdbcTemplate.queryForObject(INSERT_TAG_IGNORE_DUPLICATE, Long.class, tag.getName());
        }
        return jdbcTemplate.queryForObject(INSERT_TAG, Long.class, tag.getName());
    }

    @Override
    public void update(Tag tag) {
        logger.debug("Updating tag: {}", tag);
        jdbcTemplate.update(UPDATE_TAG, tag.getName(), tag.getId());
    }

    @Override
    public void delete(Long id) {
        logger.debug("Deleting tag by ID: {}", id);
        jdbcTemplate.update(DELETE_TAG, id);
    }
}
