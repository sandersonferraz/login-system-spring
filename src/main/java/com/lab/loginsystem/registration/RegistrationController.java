package com.lab.loginsystem.registration;

import com.lab.loginsystem.registration.domain.RegistrationRequest;
import com.lab.loginsystem.registration.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    public String register(@RequestBody RegistrationRequest request){
        return registrationService.register(request);
    }

}