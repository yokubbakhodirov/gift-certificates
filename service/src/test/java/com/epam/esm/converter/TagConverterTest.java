package com.epam.esm.converter;

import com.epam.esm.config.ServiceTestConfig;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.utils.TestData;
import com.epam.esm.utils.converter.TagConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ServiceTestConfig.class)
public class TagConverterTest {
    @Autowired
    private TagConverter tagConverter;

    @Test
    @DisplayName("Convert Tag to TagDto")
    public void testToDto() {
        TagDto actual = tagConverter.toDto(TestData.TAG);

        assertEquals(TestData.TAG_DTO, actual);
    }

    @Test
    @DisplayName("Convert TagDto to Tag")
    public void testTOEntity() {
        Tag actual = tagConverter.toEntity(TestData.TAG_DTO);

        assertEquals(TestData.TAG, actual);
    }
}
