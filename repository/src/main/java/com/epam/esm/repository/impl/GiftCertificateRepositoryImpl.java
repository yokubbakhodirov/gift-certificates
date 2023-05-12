package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.util.mapper.GiftCertificateRowMapper;
import com.epam.esm.util.mapper.TagRowMapper;
import lombok.AllArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.epam.esm.util.constant.SqlQueries.*;


@Repository
@AllArgsConstructor
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    private final JdbcTemplate jdbcTemplate;
    private final GiftCertificateRowMapper giftCertificateRowMapper;
    private final TagRowMapper tagRowMapper;

    @Override
    public GiftCertificate findById(Long id) {
        GiftCertificate giftCertificate = jdbcTemplate.queryForObject(FIND_GIFT_CERTIFICATE_BY_ID, giftCertificateRowMapper, id);
        setTags(giftCertificate);
        return giftCertificate;
    }

    @Override
    public List<GiftCertificate> findAll(String tagName, String search, String sort) {
        if (tagName == null && search == null && sort == null) {
            List<GiftCertificate> giftCertificates = jdbcTemplate.query(FIND_ALL_GIFT_CERTIFICATES, giftCertificateRowMapper);
            giftCertificates.forEach(this::setTags);
            return giftCertificates;
        }

        StringBuilder sql = new StringBuilder(FIND_ALL_GIFT_CERTIFICATES_BY_PARAMS);

        if (tagName != null || search != null) {
            sql.append(" WHERE ");
        }

        if (tagName != null) {
            tagName = "'" + tagName + "'";
            sql.append("t.name = ").append(tagName);
        }

        if (tagName != null && search != null ) {
            sql.append(" AND ");
        }

        if (search != null) {
            search = "'%" + search + "%'";
            sql.append("(gc.name LIKE ").append(search).append(" OR gc.description LIKE ").append(search).append(")");
        }

        if (sort != null) {
            sql.append(" ORDER BY ");
            sql.append(switch (sort) {
                case "date_asc" -> "gc.create_date ASC;";
                case "date_desc" -> "gc.create_date DESC;";
                case "name_asc" -> "gc.name ASC;";
                case "name_desc" -> "gc.name DESC;";
                default -> "gc.create_date ASC";
            });
        }

        List<GiftCertificate> giftCertificates = jdbcTemplate.query(sql.toString(), giftCertificateRowMapper);
        giftCertificates.forEach(this::setTags);
        return giftCertificates;
    }

    @Override
    public Long insert(GiftCertificate giftCertificate) {
        return jdbcTemplate.queryForObject(
                INSERT_GIFT_CERTIFICATE, Long.class,
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration());
    }

    @Override
    public void update(GiftCertificate giftCertificate) {
        jdbcTemplate.update(UPDATE_GIFT_CERTIFICATE,
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration(),
                giftCertificate.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE_GIFT_CERTIFICATE, id);
    }

    @Override
    public void insertGiftCertificateTagRelation(GiftCertificate giftCertificate) {
        if (giftCertificate.getTags() == null || giftCertificate.getTags().size() == 0) {
            return;
        }
        giftCertificate.getTags().forEach(tag -> {
            try {
                System.out.println("s");
                jdbcTemplate.update(INSERT_GIFT_CERTIFICATE_TAG, giftCertificate.getId(), tag.getId());
            } catch (DuplicateKeyException e) {
            }
        });
    }

    public void setTags(GiftCertificate giftCertificate) {
        if (giftCertificate != null) {
            giftCertificate.getTags().addAll(
                    jdbcTemplate.query(FIND_ALL_TAGS_OF_GIFT_CERTIFICATE, tagRowMapper, giftCertificate.getId()));
        }
    }
}
