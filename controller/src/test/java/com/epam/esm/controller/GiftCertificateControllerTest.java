package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.service.GiftCertificateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

import static com.epam.esm.utils.TestData.GIFT_CERTIFICATE_DTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GiftCertificateControllerTest {
    @Mock
    private BindingResult bindingResult;
    @Mock
    private GiftCertificateService giftCertificateService;
    @InjectMocks
    private GiftCertificateController giftCertificateController;

    @Test
    public void testGetById() {
        when(giftCertificateService.findById(GIFT_CERTIFICATE_DTO.getId())).thenReturn(GIFT_CERTIFICATE_DTO);
        ResponseEntity<GiftCertificateDto> response = giftCertificateController.getById(GIFT_CERTIFICATE_DTO.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(GIFT_CERTIFICATE_DTO, response.getBody());
    }

    @Test
    public void testGetAll() {
        String tag = "tag";
        String search = "name/description";
        String sort = "date_asc";
        when(giftCertificateService.findAll(tag, search, sort))
                .thenReturn(Collections.singletonList(GIFT_CERTIFICATE_DTO));
        ResponseEntity<List<GiftCertificateDto>> response = giftCertificateController
                .getAll(tag, search, sort);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(GIFT_CERTIFICATE_DTO, response.getBody().get(0));
    }

    @Test
    public void testInsert() {
        String uri = "http://localhost:8080";
        String location = "http://localhost:8080/gift-certificates/1";
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(uri);

        when(giftCertificateService.insert(GIFT_CERTIFICATE_DTO)).thenReturn(GIFT_CERTIFICATE_DTO.getId());
        ResponseEntity<String> response =
                giftCertificateController.insert(GIFT_CERTIFICATE_DTO, bindingResult, uriBuilder);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(location, response.getHeaders().getLocation().toString());
        verify(giftCertificateService, times(1)).insert(GIFT_CERTIFICATE_DTO);
    }

    @Test
    public void testUpdate() {
        ResponseEntity<Void> response = giftCertificateController.update(GIFT_CERTIFICATE_DTO.getId(),
                GIFT_CERTIFICATE_DTO, bindingResult);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(giftCertificateService, times(1)).update(GIFT_CERTIFICATE_DTO);
    }

    @Test
    public void testDelete() {
        ResponseEntity<Void> response = giftCertificateController.delete(GIFT_CERTIFICATE_DTO.getId());

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(giftCertificateService, times(1)).delete(GIFT_CERTIFICATE_DTO.getId());
    }
}
