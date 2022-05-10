package com.lab.loginsystem.registration.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationTokenRepository  extends JpaRepository<ConfirmationToken, Long> {
    // TODO: 1:11:06 Repository
}
