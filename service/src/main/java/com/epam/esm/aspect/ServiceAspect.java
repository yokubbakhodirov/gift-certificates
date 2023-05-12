package com.epam.esm.aspect;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.DataAccessFailureException;
import com.epam.esm.exception.DataNotFoundException;
import com.epam.esm.exception.DuplicateEntryException;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.epam.esm.utils.constant.ExceptionMessage.*;

@Component
@Aspect
public class ServiceAspect {
    private final Logger logger = LoggerFactory.getLogger(ServiceAspect.class);

    @Around("execution(* com.epam.esm.service.impl.*.*(..))")
    public Object logAndHandleExceptions(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        try {
            logger.info("Entering method: {} with argument(s): {}", methodName, Arrays.toString(args));
            Object result = joinPoint.proceed();
            if (result != null) {
                logger.info("Exiting method: {} with result: {}", methodName, result);
            } else {
                logger.info("Exiting method: {}", methodName);
            }
            return result;
        } catch (EmptyResultDataAccessException e) {
            String message = getMessageForNotFound(args[0], joinPoint);
            logger.error(message);
            throw new DataNotFoundException(message);
        } catch (DataIntegrityViolationException e) {
            String message = getMessageForIntegrityValidation(args[0]);
            logger.error(message);
            throw new DuplicateEntryException(message);
        } catch (DataAccessException e) {
            Object arg = args.length > 0 ? args[0] : null;
            String message = getMessageForDataAccessFailure(arg, joinPoint);
            logger.error(message + e.getMessage());
            throw new DataAccessFailureException(message, e);
        }
    }

    private String getMessageForNotFound(Object arg, ProceedingJoinPoint joinPoint) {
        Class<?> className = joinPoint.getTarget().getClass();
        if (className.equals(GiftCertificateServiceImpl.class)) {
            if (arg != null && !arg.getClass().equals(Long.class)) {
                arg = ((GiftCertificateDto) arg).getId();
            }
            return String.format(GIFT_CERTIFICATE_NOT_FOUND, arg);
        } else {
            if (arg != null && !arg.getClass().equals(Long.class)) {
                arg = ((TagDto) arg).getId();
            }
            return String.format(TAG_NOT_FOUND, arg);
        }
    }

    private String getMessageForIntegrityValidation(Object arg) {
        return String.format(TAG_INSERT_DUPLICATE_ENTRY, arg);
    }

    private String getMessageForDataAccessFailure(Object arg, ProceedingJoinPoint joinPoint) {
        Class<?> className = joinPoint.getTarget().getClass();
        String methodName = joinPoint.getSignature().getName();
        String message;
        if (className.equals(GiftCertificateServiceImpl.class)) {
            if (arg != null) {
                if (arg.getClass().equals(Long.class)) {
                    message = String.format(GIFT_CERTIFICATE_FAILED_WITH_ID, methodName, arg);
                } else {
                    message = String.format(GIFT_CERTIFICATE_FAILED_WITH_ARG, methodName, arg);
                }
            } else {
                message = String.format(GIFT_CERTIFICATE_FAILED, methodName);
            }
        } else {
            if (arg != null) {
                if (arg.getClass().equals(Long.class)) {
                    message = String.format(TAG_FAILED_WITH_ID, methodName, arg);
                } else {
                    message = String.format(TAG_FAILED_WITH_ARG, methodName, arg);
                }
            } else {
                message = String.format(TAG_FAILED, methodName);
            }
        }
        return message;
    }
}
