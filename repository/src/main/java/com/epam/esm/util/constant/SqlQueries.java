package com.epam.esm.util.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SqlQueries {
    // sql queries for gift_certificate table
    public static final String FIND_GIFT_CERTIFICATE_BY_ID = "SELECT * FROM gift_certificate WHERE id = ?;";
    public static final String FIND_ALL_GIFT_CERTIFICATES = "SELECT * FROM gift_certificate;";
    public static final String FIND_ALL_GIFT_CERTIFICATES_BY_PARAMS = "SELECT DISTINCT gc.* FROM gift_certificate gc " +
            "LEFT JOIN gift_certificate_tag gct ON gc.id = gct.gift_certificate_id " +
            "LEFT JOIN tag t ON t.id = gct.tag_id";
    public static final String FIND_ALL_TAGS_OF_GIFT_CERTIFICATE = "SELECT t.* FROM tag t JOIN gift_certificate_tag gct " +
            "ON t.id = gct.tag_id WHERE gct.gift_certificate_id = ?";
    public static final String INSERT_GIFT_CERTIFICATE = "INSERT INTO gift_certificate(name, description, price, duration) " +
            "VALUES (?, ?, ?, ?) returning id;";
    public static final String UPDATE_GIFT_CERTIFICATE = "UPDATE gift_certificate SET name = ?, description = ?, price = ?, " +
            "duration = ?, last_update_date = CURRENT_TIMESTAMP WHERE id = ?;";
    public static final String DELETE_GIFT_CERTIFICATE = "DELETE FROM gift_certificate WHERE id = ?;";

    // sql queries for tag table
    public static final String FIND_TAG_BY_ID = "SELECT * FROM tag WHERE id = ?;";
    public static final String FIND_TAG_BY_NAME = "SELECT * FROM tag WHERE name = ?;";
    public static final String FIND_ALL_TAGS = "SELECT * FROM tag;";
    public static final String INSERT_TAG = "INSERT INTO tag (name) VALUES (?) RETURNING id;";
    public static final String INSERT_TAG_IGNORE_DUPLICATE = "INSERT INTO tag (name) VALUES (?) ON CONFLICT (name) " +
            "DO NOTHING RETURNING id;";
    public static final String UPDATE_TAG = "UPDATE tag SET name = ? WHERE id = ?;";
    public static final String DELETE_TAG = "DELETE FROM tag WHERE id = ?;";

    // sql queries for gift_certificate_tag table
    public static final String INSERT_GIFT_CERTIFICATE_TAG = "INSERT INTO gift_certificate_tag VALUES (?, ?)";
}
