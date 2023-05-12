package com.epam.esm.utils.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionMessage {
    // gift certificate
    public static final String GIFT_CERTIFICATE_NOT_FOUND = "Failed to find gift certificate with ID: %s.";
    public static final String GIFT_CERTIFICATE_FAILED_WITH_ID = "Failed to perform %s operation on gift certificate with id: %s.";
    public static final String GIFT_CERTIFICATE_FAILED_WITH_ARG = "Failed to perform %s operation on gift certificate: %s.";
    public static final String GIFT_CERTIFICATE_FAILED = "Failed to perform %s operation on gift certificate.";

    // tag
    public static final String TAG_NOT_FOUND = "Failed to find tag with ID: %s.";
    public static final String TAG_FAILED_WITH_ID = "Failed to perform %s operation on tag with id: %s.";
    public static final String TAG_FAILED_WITH_ARG = "Failed to perform %s operation on tag: %s.";
    public static final String TAG_FAILED = "Failed to perform %s operation on tag.";
    public static final String TAG_INSERT_DUPLICATE_ENTRY = "Failed to insert tag due to data integrity violation: %s. ";
}
