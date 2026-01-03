package com.sadmag.macros_v2.profile.validators;

import com.sadmag.macros_v2.profile.ProfileRequest;
import com.sadmag.macros_v2.user.exception.ValidationException;
import com.sadmag.macros_v2.user.validators.Validator;
import org.springframework.stereotype.Service;

@Service
public class GenderValidator implements Validator<ProfileRequest> {

    @Override
    public void validate(ProfileRequest profileRequest) {
        var gender = profileRequest.getGender();
        if (gender != 'M' && gender != 'F') throw new ValidationException("Gender not recognized, use only \"M\" or \"F\"");
    }
}