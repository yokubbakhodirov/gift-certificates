package com.epam.esm.util.mapper;

import com.epam.esm.entity.Tag;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.esm.util.constant.ColumnNames.TAG_ID;
import static com.epam.esm.util.constant.ColumnNames.TAG_NAME;

@Component
public class TagRowMapper implements RowMapper<Tag> {
    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Tag
                .builder()
                .id(rs.getLong(TAG_ID))
                .name(rs.getString(TAG_NAME))
                .build();
    }
}
