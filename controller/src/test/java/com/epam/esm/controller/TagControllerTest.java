package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.service.TagService;
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

import static com.epam.esm.utils.TestData.TAG_DTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TagControllerTest {
    @Mock
    private BindingResult bindingResult;
    @Mock
    private TagService tagService;
    @InjectMocks
    private TagController tagController;

    @Test
    public void testGetById() {
        when(tagService.findById(TAG_DTO.getId())).thenReturn(TAG_DTO);
        ResponseEntity<TagDto> response = tagController.getById(TAG_DTO.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(TAG_DTO, response.getBody());
    }

    @Test
    public void testGetAll() {
        when(tagService.findAll()).thenReturn(Collections.singletonList(TAG_DTO));
        ResponseEntity<List<TagDto>> response = tagController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(TAG_DTO, response.getBody().get(0));
    }

    @Test
    public void testInsert() {
        String uri = "http://localhost:8080";
        String location = "http://localhost:8080/tags/1";
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(uri);

        when(tagService.insert(TAG_DTO)).thenReturn(TAG_DTO.getId());
        ResponseEntity<String> response = tagController.insert(TAG_DTO, bindingResult, uriBuilder);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(location, response.getHeaders().getLocation().toString());
        verify(tagService, times(1)).insert(TAG_DTO);
    }

    @Test
    public void testUpdate() {
        ResponseEntity<Void> response = tagController.update(TAG_DTO.getId(), TAG_DTO, bindingResult);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(tagService, times(1)).update(TAG_DTO);
    }

    @Test
    public void testDelete() {
        ResponseEntity<Void> response = tagController.delete(TAG_DTO.getId());

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(tagService, times(1)).delete(TAG_DTO.getId());
    }
}
