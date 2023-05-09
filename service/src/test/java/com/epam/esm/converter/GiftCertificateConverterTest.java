package com.epam.esm.converter;

import com.epam.esm.config.ServiceTestConfig;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.utils.TestData;
import com.epam.esm.utils.converter.GiftCertificateConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ServiceTestConfig.class)
public class GiftCertificateConverterTest {
    @Autowired
    private GiftCertificateConverter giftCertificateConverter;

    @Test
    @DisplayName("Convert GiftCertificate to GiftCertificateDto")
    public void testToDto() {
        GiftCertificateDto actual = giftCertificateConverter.toDto(TestData.GIFT_CERTIFICATE);

        assertEquals(TestData.GIFT_CERTIFICATE_DTO, actual);
    }

    @Test
    @DisplayName("Convert GiftCertificateDto to GiftCertificate")
    public void testTOEntity() {
        GiftCertificate actual = giftCertificateConverter.toEntity(TestData.GIFT_CERTIFICATE_DTO);

        assertEquals(TestData.GIFT_CERTIFICATE, actual);
    }
}
