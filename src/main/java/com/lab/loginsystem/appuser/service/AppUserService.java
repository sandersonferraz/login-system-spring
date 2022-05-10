package com.lab.loginsystem.appuser.service;

import com.lab.loginsystem.appuser.domain.AppUSerRepository;
import com.lab.loginsystem.appuser.domain.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {
    private final static String USER_NOT_FOUND_MSG =
            "User with email %s not found.";
    private final AppUSerRepository appUSerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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
        // TODO: Send confirmation token
        return  "It works";
    }
}
