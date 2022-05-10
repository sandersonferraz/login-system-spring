package com.lab.loginsystem.registration.service;

import com.lab.loginsystem.appuser.domain.AppUser;
import com.lab.loginsystem.appuser.domain.AppUserRole;
import com.lab.loginsystem.appuser.service.AppUserService;
import com.lab.loginsystem.registration.EmailValidator;
import com.lab.loginsystem.registration.domain.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;

    private final EmailValidator emailValidator;

    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (!isValidEmail) {
            throw new IllegalStateException("Email not valid!");
        }
        return appUserService.signUpUser(
                new AppUser(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        AppUserRole.USE
                )
        );
    }
}
