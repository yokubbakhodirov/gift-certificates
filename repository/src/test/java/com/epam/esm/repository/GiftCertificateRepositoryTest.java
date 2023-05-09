package com.epam.esm.repository;

import com.epam.esm.config.RepositoryTestConfig;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.impl.GiftCertificateRepositoryImpl;
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
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

import static com.epam.esm.utils.TestData.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RepositoryTestConfig.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GiftCertificateRepositoryTest {
    @Autowired
    GiftCertificateRepositoryImpl giftCertificateRepository;

    @ParameterizedTest
    @MethodSource("getGiftCertificateParams")
    @Order(1)
    public void testFindById(GiftCertificate expected, Long id) {
        GiftCertificate actual = giftCertificateRepository.findById(id);

        assertTrue(isEqual(expected, actual));
    }

    @Test
    @Order(2)
    public void testFindByIdNotFound() {
        assertThrows(EmptyResultDataAccessException.class, () -> giftCertificateRepository.findById(100L));
    }

    @Test
    @Order(3)
    public void testFindAllWithoutArg() {
        List<GiftCertificate> giftCertificates = giftCertificateRepository.findAll(null, null, null);

        assertEquals(4, giftCertificates.size());
    }

    @Test
    @Order(4)
    public void testFindAllByTag() {
//        TestData.GIFT_CERTIFICATE_1 has TAG_1 and TAG_2
        List<GiftCertificate> giftCertificates = giftCertificateRepository.findAll(TAG_1.getName(), null, null);
        assertEquals(1, giftCertificates.size());
        assertTrue(isEqual(GIFT_CERTIFICATE_1, giftCertificates.get(0)));

//        TestData.GIFT_CERTIFICATE_2 has TAG_3 and TAG_4
        giftCertificates = giftCertificateRepository.findAll(TAG_4.getName(), null, null);
        assertEquals(1, giftCertificates.size());
        assertTrue(isEqual(GIFT_CERTIFICATE_2, giftCertificates.get(0)));
    }

    @Test
    @Order(5)
    public void testFindAllByPartOfNameOrDescription() {
//         search arg must be part of either name or description
//         all gift certificate names start with 'gift' and descriptions with 'des'
        List<GiftCertificate> giftCertificates = giftCertificateRepository.findAll(null, "gift", null);
        assertEquals(4, giftCertificates.size());

        giftCertificates = giftCertificateRepository.findAll(null, "des", null);
        assertEquals(4, giftCertificates.size());

//         2 gift certificate names start with 'gift certificate' and descriptions with 'description'
        giftCertificates = giftCertificateRepository.findAll(null, "gift certificate", null);
        assertEquals(2, giftCertificates.size());

        giftCertificates = giftCertificateRepository.findAll(null, "description", null);
        assertEquals(2, giftCertificates.size());

//         one's name is 'gift certificate 3'
        giftCertificates = giftCertificateRepository.findAll(null, "gift certificate 3", null);
        assertEquals(1, giftCertificates.size());
        assertTrue(isEqual(GIFT_CERTIFICATE_3, giftCertificates.get(0)));

//        one's description is 'description 4'
        giftCertificates = giftCertificateRepository.findAll(null, "description 4", null);
        assertEquals(1, giftCertificates.size());
        assertTrue(isEqual(GIFT_CERTIFICATE_4, giftCertificates.get(0)));
    }

    @Test
    @Order(6)
    public void testFindAllSorted() {
        List<GiftCertificate> giftCertificates = giftCertificateRepository.findAll(null, null, "name_asc");
        assertTrue(isEqual(GIFT_CERTIFICATE_1, giftCertificates.get(0)));
        assertTrue(isEqual(GIFT_CERTIFICATE_2, giftCertificates.get(1)));
        assertTrue(isEqual(GIFT_CERTIFICATE_3, giftCertificates.get(2)));
        assertTrue(isEqual(GIFT_CERTIFICATE_4, giftCertificates.get(3)));

        giftCertificates = giftCertificateRepository.findAll(null, null, "name_desc");
        assertTrue(isEqual(GIFT_CERTIFICATE_4, giftCertificates.get(0)));
        assertTrue(isEqual(GIFT_CERTIFICATE_3, giftCertificates.get(1)));
        assertTrue(isEqual(GIFT_CERTIFICATE_2, giftCertificates.get(2)));
        assertTrue(isEqual(GIFT_CERTIFICATE_1, giftCertificates.get(3)));
    }

    @Test
    @Order(7)
    public void testFindAllMixed() {
        List<GiftCertificate> giftCertificates = giftCertificateRepository
                .findAll(null, "gift certificate", "name_asc");
        assertEquals(2, giftCertificates.size());
        assertTrue(isEqual(GIFT_CERTIFICATE_3, giftCertificates.get(0)));
        assertTrue(isEqual(GIFT_CERTIFICATE_4, giftCertificates.get(1)));

        giftCertificates = giftCertificateRepository.findAll(null, "description", "name_desc");
        assertEquals(2, giftCertificates.size());
        assertTrue(isEqual(GIFT_CERTIFICATE_4, giftCertificates.get(0)));
        assertTrue(isEqual(GIFT_CERTIFICATE_3, giftCertificates.get(1)));

        giftCertificates = giftCertificateRepository.findAll(TAG_1.getName(), "gift certificate", "name_asc");
        assertEquals(0, giftCertificates.size());
    }

    @Test
    @Order(8)
    public void testInsert() {
        Long id = giftCertificateRepository.insert(GIFT_CERTIFICATE_5);
        assertEquals(5L, id);

        GiftCertificate actual = giftCertificateRepository.findById(id);
        assertTrue(isEqual(GIFT_CERTIFICATE_5, actual));
    }

    @Test
    @Order(9)
    public void testUpdate() {
        GIFT_CERTIFICATE_5.setPrice(50d);
        giftCertificateRepository.update(GIFT_CERTIFICATE_5);

        GiftCertificate actual = giftCertificateRepository.findById(GIFT_CERTIFICATE_5.getId());
        assertTrue(isEqual(GIFT_CERTIFICATE_5, actual));
    }

    @Test
    @Order(10)
    public void testDelete() {
        giftCertificateRepository.delete(GIFT_CERTIFICATE_5.getId());
        assertThrows(EmptyResultDataAccessException.class,
                () -> giftCertificateRepository.findById(GIFT_CERTIFICATE_5.getId()));
    }

    @Test
    @Order(11)
    public void testInsertGiftCertificateTagRelation() {
        GIFT_CERTIFICATE_3.setTags(new HashSet<>(Arrays.asList(TAG_1, TAG_2, TAG_3)));
        giftCertificateRepository.insertGiftCertificateTagRelation(GIFT_CERTIFICATE_3);

        GiftCertificate giftCertificate = giftCertificateRepository.findById(GIFT_CERTIFICATE_3.getId());
        assertEquals(GIFT_CERTIFICATE_3.getTags(), giftCertificate.getTags());
    }

    private boolean isEqual(GiftCertificate giftCertificate1, GiftCertificate giftCertificate2) {
        if (giftCertificate1 != null && giftCertificate2 != null) {
            return giftCertificate1.getName().equals(giftCertificate2.getName()) &&
                    giftCertificate1.getDescription().equals(giftCertificate2.getDescription()) &&
                    giftCertificate1.getPrice().equals(giftCertificate2.getPrice()) &&
                    giftCertificate1.getDuration().equals(giftCertificate2.getDuration());
        }
        return false;
    }

    private static Stream<Arguments> getGiftCertificateParams() {
        return Stream.of(
                Arguments.of(TestData.GIFT_CERTIFICATE_1, TestData.GIFT_CERTIFICATE_1.getId()),
                Arguments.of(TestData.GIFT_CERTIFICATE_2, TestData.GIFT_CERTIFICATE_2.getId()),
                Arguments.of(TestData.GIFT_CERTIFICATE_3, TestData.GIFT_CERTIFICATE_3.getId()),
                Arguments.of(TestData.GIFT_CERTIFICATE_4, TestData.GIFT_CERTIFICATE_4.getId())
        );
    }
}

