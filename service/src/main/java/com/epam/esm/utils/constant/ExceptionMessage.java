package com.epam.esm.utils.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionMessage {
    // gift certificate
    public static final String GIFT_CERTIFICATE_NOT_FOUND = "Failed to find gift certificate with ID: %s. ";

    public static final String GIFT_CERTIFICATE_FIND_ALL_FAILED = "Failed to find all gift certificates. ";
    public static final String GIFT_CERTIFICATE_INSERT_FAILED = "Failed to insert gift certificate: %s. ";
    public static final String GIFT_CERTIFICATE_UPDATE_FAILED = "Failed to update gift certificate: %s. ";
    public static final String GIFT_CERTIFICATE_DELETE_FAILED = "Failed to delete gift certificate with ID: %s. ";
    public static final String GIFT_CERTIFICATE_INSERT_TAG_FAILED = "Failed to insert tag for gift certificate: %s. ";

    // tag
    public static final String TAG_NOT_FOUND = "Failed to find tag with id: %s. ";

    public static final String TAG_FIND_ALL_FAILED = "Failed to find tags. ";
    public static final String TAG_INSERT_FAILED = "Failed to insert tag %s. ";
    public static final String TAG_INSERT_DUPLICATE_ENTRY = "Failed to insert tag due to data integrity violation: %s. ";
    public static final String TAG_UPDATE_FAILED = "Failed to update %s. ";
    public static final String TAG_DELETE_FAILED = "Failed to delete %s. ";
}
