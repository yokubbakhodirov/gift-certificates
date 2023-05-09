package com.epam.esm.config;

import com.epam.esm.utils.converter.GiftCertificateConverter;
import com.epam.esm.utils.converter.TagConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceTestConfig {
    @Bean
    public GiftCertificateConverter giftCertificateConverter() {
        return new GiftCertificateConverter();
    }

    @Bean
    public TagConverter tagConverter() {
        return new TagConverter();
    }
}
