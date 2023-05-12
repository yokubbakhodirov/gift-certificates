package com.epam.esm.utils;

import com.epam.esm.exception.BindingResultException;
import lombok.experimental.UtilityClass;
import org.springframework.validation.BindingResult;

import java.util.List;

@UtilityClass
public class BindingResultChecker {
    public static void check(BindingResult bindingResult) throws BindingResultException {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + " " + error.getDefaultMessage())
                    .toList();

            throw new BindingResultException(errors.toArray(new String[errors.size()]));
        }
    }
}
