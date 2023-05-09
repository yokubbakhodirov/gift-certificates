package com.epam.esm.utils.converter;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GiftCertificateConverter {
    public GiftCertificateDto toDto(GiftCertificate certificate) {
        return GiftCertificateDto.builder()
                .id(certificate.getId())
                .name(certificate.getName())
                .description(certificate.getDescription())
                .price(certificate.getPrice())
                .duration(certificate.getDuration())
                .createDate(dateTimeToString(certificate.getCreateDate()))
                .lastUpdateDate(dateTimeToString(certificate.getLastUpdateDate()))
                .tags(tagEntityToDto(certificate.getTags()))
                .build();
    }

    public GiftCertificate toEntity(GiftCertificateDto dto) {
        return GiftCertificate.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .duration(dto.getDuration())
                .createDate(stringToDateTime(dto.getCreateDate()))
                .lastUpdateDate(stringToDateTime(dto.getLastUpdateDate()))
                .tags(tagDtoToEntity(dto.getTags()))
                .build();
    }

    private String dateTimeToString(LocalDateTime localDateTime) {
        return localDateTime != null ? DateTimeFormatter.ISO_DATE_TIME.format(localDateTime) : null;
    }

    private LocalDateTime stringToDateTime(String localDateTimeString) {
        return localDateTimeString != null ? LocalDateTime.parse(localDateTimeString, DateTimeFormatter.ISO_DATE_TIME) : null;
    }

    private Set<Tag> tagDtoToEntity(TagDto[] tags) {
        return tags != null ? Arrays.stream(tags)
                .map(tagDto -> Tag.builder().id(tagDto.getId()).name(tagDto.getName()).build())
                .collect(Collectors.toSet()) : new HashSet<>();
    }

    private TagDto[] tagEntityToDto(Set<Tag> tags) {
        return tags != null ? tags
                .stream()
                .map(tag -> TagDto.builder().id(tag.getId()).name(tag.getName()).build())
                .toArray(TagDto[]::new) : new TagDto[]{};
    }
}
