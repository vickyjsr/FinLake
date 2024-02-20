package com.finlake.common.helper;

import com.finlake.common.enums.ResponseCode;
import com.finlake.common.exception.BadRequestException;
import jakarta.validation.*;

import java.util.Set;

public interface RequestValidator {

    static <T> void validateRequest(T object, String requestId) {
        try {
            validate(object);
        } catch (Exception e) {
            throw new BadRequestException(requestId, ResponseCode.BAD_REQUEST);
        }
    }

    static void validate(Object object) {
        Validator validator;
        try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            validator = validatorFactory.getValidator();
        }
        Set<ConstraintViolation<Object>> validate = validator.validate(object);
        if (!validate.isEmpty()) {
            throw new ConstraintViolationException(validate);
        }
    }
}
