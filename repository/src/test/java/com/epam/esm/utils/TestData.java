package com.epam.esm.utils;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.HashSet;

@UtilityClass
public class TestData {
    public static final GiftCertificate GIFT_CERTIFICATE_1;
    public static final GiftCertificate GIFT_CERTIFICATE_2;
    public static final GiftCertificate GIFT_CERTIFICATE_3;
    public static final GiftCertificate GIFT_CERTIFICATE_4;
    public static final GiftCertificate GIFT_CERTIFICATE_5;
    public static final Tag TAG_1;
    public static final Tag TAG_2;
    public static final Tag TAG_3;
    public static final Tag TAG_4;
    public static final Tag TAG_5;

    static {
        TAG_1 = Tag.builder()
                .id(1L)
                .name("tag 1")
                .build();
        TAG_2 = Tag.builder()
                .id(2L)
                .name("tag 2")
                .build();
        TAG_3 = Tag.builder()
                .id(3L)
                .name("tag 3")
                .build();
        TAG_4 = Tag.builder()
                .id(4L)
                .name("tag 4")
                .build();
        TAG_5 = Tag.builder()
                .id(5L)
                .name("tag 5")
                .build();

        GIFT_CERTIFICATE_1 = GiftCertificate.builder()
                .id(1L)
                .name("gift 1")
                .description("des 1")
                .price(10d)
                .duration(20)
                .tags(new HashSet<>(Arrays.asList(TAG_1, TAG_2)))
                .build();
        GIFT_CERTIFICATE_2 = GiftCertificate.builder()
                .id(2L)
                .name("gift 2")
                .description("des 2")
                .price(10d)
                .duration(20)
                .tags(new HashSet<>(Arrays.asList(TAG_3, TAG_4)))
                .build();
        GIFT_CERTIFICATE_3 = GiftCertificate.builder()
                .id(3L)
                .name("gift certificate 3")
                .description("description 3")
                .price(10d)
                .duration(20)
                .build();
        GIFT_CERTIFICATE_4 = GiftCertificate.builder()
                .id(4L)
                .name("gift certificate 4")
                .description("description 4")
                .price(10d)
                .duration(20)
                .build();
        GIFT_CERTIFICATE_5 = GiftCertificate.builder()
                .id(5L)
                .name("gift certificate 5")
                .description("description 5")
                .price(10d)
                .duration(20)
                .build();
    }
}
