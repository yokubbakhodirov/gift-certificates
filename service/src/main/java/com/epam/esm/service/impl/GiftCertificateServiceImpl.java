package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.utils.converter.GiftCertificateConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository giftCertificateRepository;
    private final TagRepository tagRepository;
    private final GiftCertificateConverter giftCertificateConverter;

    @Override
    public GiftCertificateDto findById(Long id) {
        return giftCertificateConverter.toDto(giftCertificateRepository.findById(id));
    }

    @Override
    public List<GiftCertificateDto> findAll(String tagName, String search, String sort) {
        return giftCertificateRepository.findAll(tagName, search, sort).stream()
                .map(giftCertificateConverter::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Long insert(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = giftCertificateConverter.toEntity(giftCertificateDto);
        Long id = giftCertificateRepository.insert(giftCertificate);
        giftCertificate.setId(id);

        insertTagsForGiftCertificate(giftCertificate.getTags());
        giftCertificateRepository.insertGiftCertificateTagRelation(giftCertificate);
        return id;
    }

    @Override
    public void update(GiftCertificateDto giftCertificateDto) {
        Long id = giftCertificateDto.getId();
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
    }

    @Override
    public void delete(Long id) {
        giftCertificateRepository.findById(id);
        giftCertificateRepository.delete(id);
    }

    public void insertTagsForGiftCertificate(Set<Tag> tags) {
        if (tags == null || tags.size() == 0) {
            return;
        }

        for (Tag tag : tags) {
            try {
                Long id = tagRepository.insert(tag, true);
                tag.setId(id);
            } catch (EmptyResultDataAccessException e) {
                Optional.ofNullable(tagRepository.findByName(tag.getName()))
                        .ifPresent(tag1 -> tag.setId(tag1.getId()));
            }
        }
    }

}
