package com.example.demo.invadersdetector.validator;

import com.example.demo.invadersdetector.dto.DetectRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class StringLengthValidator implements ConstraintValidator<AllStringsSameLength, DetectRequest> {

    @Override
    public boolean isValid(DetectRequest detectRequest, ConstraintValidatorContext constraintValidatorContext) {
        List<String> data = detectRequest.getRadarData();
        if (data == null || data.isEmpty()) {
            return true;
        }
        int expectedLength = 0;
        for (String dataRow : data) {
            if (dataRow == null) {
                return false;
            }
            int currentLength = dataRow.length();
            if (expectedLength == 0) {
                expectedLength = currentLength;
            } else if (currentLength != expectedLength) {
                return false;
            }
        }
        return true;
    }
}
