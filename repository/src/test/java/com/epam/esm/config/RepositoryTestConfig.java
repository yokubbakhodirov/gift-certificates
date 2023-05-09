package com.epam.esm.config;

import com.epam.esm.repository.impl.GiftCertificateRepositoryImpl;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.util.mapper.GiftCertificateRowMapper;
import com.epam.esm.util.mapper.TagRowMapper;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;


@Testcontainers
@Configuration
public class RepositoryTestConfig {
    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
            .withUsername("username")
            .withPassword("password")
            .withInitScript("data.sql");

    @Bean
    public DataSource dataSource() {
        postgres.start();
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(postgres.getJdbcUrl());
        dataSource.setUsername(postgres.getUsername());
        dataSource.setPassword(postgres.getPassword());
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public GiftCertificateRowMapper giftCertificateRowMapper() {
        return new GiftCertificateRowMapper();
    }

    @Bean
    public TagRowMapper tagRowMapper() {
        return new TagRowMapper();
    }

    @Bean
    public GiftCertificateRepositoryImpl giftCertificateRepository(JdbcTemplate jdbcTemplate,
                                                                   GiftCertificateRowMapper giftCertificateRowMapper,
                                                                   TagRowMapper tagRowMapper) {
        return new GiftCertificateRepositoryImpl(jdbcTemplate, giftCertificateRowMapper, tagRowMapper);
    }

    @Bean
    public TagRepositoryImpl tagRepository(JdbcTemplate jdbcTemplate, TagRowMapper tagRowMapper) {
        return new TagRepositoryImpl(jdbcTemplate, tagRowMapper);
    }
}
