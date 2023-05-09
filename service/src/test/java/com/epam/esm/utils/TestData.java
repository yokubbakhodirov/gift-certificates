package com.epam.esm.utils;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@UtilityClass
public class TestData {
    public static final GiftCertificate GIFT_CERTIFICATE;
    public static final GiftCertificateDto GIFT_CERTIFICATE_DTO;
    public static final Tag TAG;
    public static final TagDto TAG_DTO;

    static {
        Long id = 1L;
        String name = "John";
        String description = "description";
        Double price = 10d;
        Integer duration = 10;
        LocalDateTime createDate = LocalDateTime.now();
        LocalDateTime lastUpdateDate = LocalDateTime.now();
        TAG = new Tag(1L, "tag 1");
        TAG_DTO = new TagDto(1L, "tag 1");
        Set<Tag> tagSet = new HashSet<>(List.of(TAG));
        TagDto[] tagArray = new TagDto[]{TAG_DTO};

        GIFT_CERTIFICATE = GiftCertificate.builder()
                .id(id)
                .name(name)
                .description(description)
                .price(price)
                .duration(duration)
                .createDate(createDate)
                .lastUpdateDate(lastUpdateDate)
                .tags(tagSet)
                .build();

        GIFT_CERTIFICATE_DTO = GiftCertificateDto.builder()
                .id(id)
                .name(name)
                .description(description)
                .price(price)
                .duration(duration)
                .createDate(DateTimeFormatter.ISO_DATE_TIME.format(createDate))
                .lastUpdateDate(DateTimeFormatter.ISO_DATE_TIME.format(lastUpdateDate))
                .tags(tagArray)
                .build();
    }

}
