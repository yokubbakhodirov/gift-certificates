package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import com.epam.esm.utils.TestData;
import com.epam.esm.utils.converter.GiftCertificateConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GiftCertificateServiceTest {
    @Mock
    private GiftCertificateRepository giftCertificateRepository;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private GiftCertificateConverter giftCertificateConverter;
    @Spy
    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;

    private GiftCertificate giftCertificate;
    private GiftCertificateDto giftCertificateDto;
    private Tag tag;


    @BeforeEach
    void setup() {
        giftCertificate = TestData.GIFT_CERTIFICATE;
        giftCertificateDto = TestData.GIFT_CERTIFICATE_DTO;
        tag = TestData.TAG;

        lenient().when(giftCertificateConverter.toDto(giftCertificate)).thenReturn(giftCertificateDto);
        lenient().when(giftCertificateConverter.toEntity(giftCertificateDto)).thenReturn(giftCertificate);
    }

    @Test
    @DisplayName("Find an existing gift certificate by ID")
    public void testFindById() {
        when(giftCertificateRepository.findById(giftCertificate.getId())).thenReturn(giftCertificate);

        GiftCertificateDto result = giftCertificateService.findById(giftCertificate.getId());
        assertEquals(giftCertificateDto, result);
    }

    @Test
    @DisplayName("Find a list of gift certificates")
    public void testFindAll() {
        List<GiftCertificateDto> expected = Collections.singletonList(giftCertificateDto);
        List<GiftCertificate> giftCertificateList = Collections.singletonList(giftCertificate);

        when(giftCertificateRepository.findAll(null, null, null)).thenReturn(giftCertificateList);

        List<GiftCertificateDto> result = giftCertificateService.findAll(null, null, null);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Insert a new gift certificate")
    public void testInsert() {
        when(giftCertificateRepository.insert(giftCertificate)).thenReturn(giftCertificate.getId());
        doNothing().when(giftCertificateRepository).insertGiftCertificateTagRelation(giftCertificate);
        doNothing().when(giftCertificateService).insertTagsForGiftCertificate(giftCertificate.getTags());

        Long actualId = giftCertificateService.insert(giftCertificateDto);
        assertEquals(giftCertificate.getId(), actualId);
    }

    @Test
    @DisplayName("Update a gift certificate")
    public void testUpdate() {
        when(giftCertificateRepository.findById(giftCertificate.getId())).thenReturn(giftCertificate);
        doNothing().when(giftCertificateRepository).update(giftCertificate);
        doNothing().when(giftCertificateRepository).insertGiftCertificateTagRelation(giftCertificate);
        doNothing().when(giftCertificateService).insertTagsForGiftCertificate(giftCertificate.getTags());

        giftCertificateService.update(giftCertificateDto);
        verify(giftCertificateRepository, times(1)).update(giftCertificate);
    }

    @Test
    @DisplayName("Delete a gift certificate")
    public void testDelete() {
        when(giftCertificateRepository.findById(giftCertificate.getId())).thenReturn(giftCertificate);
        doNothing().when(giftCertificateRepository).delete(giftCertificate.getId());

        giftCertificateService.delete(giftCertificate.getId());
        verify(giftCertificateRepository, times(1)).delete(giftCertificate.getId());
    }

    @Test
    @DisplayName("Insert tags for gift certificate")
    public void testInsertTagsForGiftCertificate() {
        when(tagRepository.insert(tag, true)).thenReturn(tag.getId());

        giftCertificateService.insertTagsForGiftCertificate(giftCertificate.getTags());
        verify(tagRepository, times(1)).insert(tag, true);
    }
}

