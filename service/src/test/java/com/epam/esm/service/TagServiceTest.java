package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DataAccessFailureException;
import com.epam.esm.exception.DataNotFoundException;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.impl.TagServiceImpl;
import com.epam.esm.utils.TestData;
import com.epam.esm.utils.converter.TagConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class TagServiceTest {
    @Mock
    private TagRepository tagRepository;
    @Mock
    TagConverter tagConverter;
    @Spy
    @InjectMocks
    TagServiceImpl tagService;

    private Tag tag;
    private TagDto tagDto;

    @BeforeEach
    void setup() {
        tag = TestData.TAG;
        tagDto = TestData.TAG_DTO;

        lenient().when(tagConverter.toDto(tag)).thenReturn(tagDto);
        lenient().when(tagConverter.toEntity(tagDto)).thenReturn(tag);
    }

    @Test
    @DisplayName("Find an existing tag by ID")
    public void testFindById() {
        when(tagRepository.findById(tag.getId())).thenReturn(tag);

        TagDto result = tagService.findById(tag.getId());
        assertEquals(tagDto, result);
    }

    @Test
    @DisplayName("Throw DataNotFoundException when tag with such ID is not found")
    public void testFindByIdNotFound() {
        when(tagRepository.findById(tag.getId())).thenThrow(EmptyResultDataAccessException.class);

        assertThrows(DataNotFoundException.class, () -> tagService.findById(tag.getId()));
    }

    @Test
    @DisplayName("Throw DataAccessFailureException when there is a problem accessing the database")
    public void testFindByIDFailed() {
        // since DataAccessException is interface, throwing one of its subclasses (InvalidDataAccessResourceUsageException)
        when(tagRepository.findById(tag.getId())).thenThrow(InvalidDataAccessResourceUsageException.class);

        assertThrows(DataAccessFailureException.class, () -> tagService.findById(tag.getId()));
    }

    @Test
    @DisplayName("Find a list of tags")
    public void testFindAll() {
        List<TagDto> expected = Collections.singletonList(tagDto);
        List<Tag> tagList = Collections.singletonList(tag);

        when(tagRepository.findAll()).thenReturn(tagList);

        List<TagDto> result = tagService.findAll();
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Throw DataAccessFailureException when there is a problem accessing the database")
    public void testFindAllFailed() {
        when(tagRepository.findAll()).thenThrow(InvalidDataAccessResourceUsageException.class);

        assertThrows(DataAccessFailureException.class, () -> tagService.findAll());
    }

    @Test
    @DisplayName("Insert a new tag")
    public void testInsertNotIgnoreDuplicate() {
        when(tagRepository.insert(tag, false)).thenReturn(tag.getId());

        Long actualId = tagService.insert(tagDto);
        assertEquals(tag.getId(), actualId);
    }

    @Test
    @DisplayName("Throw DataAccessFailureException when there is a problem accessing the database")
    public void testInsertFailed() {
        when(tagRepository.insert(tag, false))
                .thenThrow(InvalidDataAccessResourceUsageException.class);

        assertThrows(DataAccessFailureException.class, () -> tagService.insert(tagDto));
    }

    @Test
    @DisplayName("Update a tag")
    public void testUpdate() {
        when(tagRepository.findById(tag.getId())).thenReturn(tag);
        doNothing().when(tagRepository).update(tag);

        tagService.update(tagDto);
        verify(tagRepository, times(1)).update(tag);
    }

    @Test
    @DisplayName("Throw DataNotFoundException when tag with such ID is not found")
    public void testUpdateNotFound() {
        when(tagRepository.findById(tagDto.getId())).thenThrow(EmptyResultDataAccessException.class);

        assertThrows(DataNotFoundException.class, () -> tagService.update(tagDto));
    }

    @Test
    @DisplayName("Throw DataAccessFailureException when there is a problem accessing the database")
    public void testUpdateFailed() {
        when(tagRepository.findById(tag.getId())).thenReturn(tag);
        doThrow(InvalidDataAccessResourceUsageException.class).when(tagRepository).update(tag);

        assertThrows(DataAccessFailureException.class, () -> tagService.update(tagDto));
    }

    @Test
    @DisplayName("Delete a tag")
    public void testDelete() {
        when(tagRepository.findById(tag.getId())).thenReturn(tag);
        doNothing().when(tagRepository).delete(tag.getId());

        tagService.delete(tag.getId());
        verify(tagRepository, times(1)).delete(tag.getId());
    }

    @Test
    @DisplayName("Throw DataNotFoundException when tag with such ID is not found")
    public void testDeleteNotFound() {
        when(tagRepository.findById(tag.getId())).thenThrow(EmptyResultDataAccessException.class);

        assertThrows(DataNotFoundException.class, () -> tagService.delete(tag.getId()));
    }

    @Test
    @DisplayName("Throw DataAccessFailureException when there is a problem accessing the database")
    public void testDeleteFailed() {
        when(tagRepository.findById(tag.getId())).thenReturn(tag);
        doThrow(InvalidDataAccessResourceUsageException.class).when(tagRepository).delete(tag.getId());

        assertThrows(DataAccessFailureException.class, () -> tagService.delete(tag.getId()));
    }
}
