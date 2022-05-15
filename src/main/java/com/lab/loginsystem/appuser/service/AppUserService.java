package com.lab.loginsystem.appuser.service;

import com.lab.loginsystem.appuser.domain.AppUSerRepository;
import com.lab.loginsystem.appuser.domain.AppUser;
import com.lab.loginsystem.registration.token.ConfirmationToken;
import com.lab.loginsystem.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {
    private final static String USER_NOT_FOUND_MSG =
            "User with email %s not found.";
    private final AppUSerRepository appUSerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return this.appUSerRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signUpUser(AppUser appUser){
        boolean isPresent = appUSerRepository.findByEmail(appUser.getEmail())
                .isPresent();
        if (isPresent){
            throw new IllegalStateException("Email already taken.");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);
        appUSerRepository.save(appUser);
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        // TODO Send email.
        return  token;
    }
}
