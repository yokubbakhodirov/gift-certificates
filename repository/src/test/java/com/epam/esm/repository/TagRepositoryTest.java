package com.epam.esm.repository;

import com.epam.esm.config.RepositoryTestConfig;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.utils.TestData;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.Stream;

import static com.epam.esm.utils.TestData.TAG_5;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RepositoryTestConfig.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TagRepositoryTest {
    @Autowired
    private TagRepositoryImpl tagRepository;

    @ParameterizedTest
    @MethodSource("getTagParams")
    @Order(1)
    public void testFindById(Tag expected, Long id) {
        Tag actual = tagRepository.findById(id);

        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    @Order(2)
    public void testFindByIdNotFound() {
        assertThrows(EmptyResultDataAccessException.class, () -> tagRepository.findById(100L));
    }

    @Test
    @Order(3)
    public void testFindAllWithoutArg() {
        List<Tag> tags = tagRepository.findAll();

        assertEquals(4, tags.size());
    }

    @Test
    @Order(4)
    public void testInsert() {
        Long id = tagRepository.insert(TAG_5, false);
        assertEquals(5L, id);

        Tag actual = tagRepository.findById(id);
        assertEquals(TAG_5.getName(), actual.getName());
    }

    @Test
    @Order(5)
    public void testInsertDuplicateEntry() {
        assertThrows(DataIntegrityViolationException.class, () ->  tagRepository.insert(TAG_5, false));
    }

    @Test
    @Order(6)
    public void testUpdate() {
        TAG_5.setName("tag 5 changed");
        tagRepository.update(TAG_5);

        Tag actual = tagRepository.findById(TAG_5.getId());
        assertEquals(TAG_5.getName(), actual.getName());
    }

    @Test
    @Order(7)
    public void testDelete() {
        tagRepository.delete(TAG_5.getId());
        assertThrows(EmptyResultDataAccessException.class,
                () -> tagRepository.findById(TAG_5.getId()));
    }

    private static Stream<Arguments> getTagParams() {
        return Stream.of(
                Arguments.of(TestData.TAG_1, TestData.TAG_1.getId()),
                Arguments.of(TestData.TAG_2, TestData.TAG_2.getId()),
                Arguments.of(TestData.TAG_3, TestData.TAG_3.getId()),
                Arguments.of(TestData.TAG_4, TestData.TAG_4.getId())
        );
    }
}
