package com.epam.esm.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BindingResultException extends RuntimeException {
    private final String[] errors;
}
