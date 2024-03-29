package com.amoncardim.paymentsystem.repositories;

import com.amoncardim.paymentsystem.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {

    UserDetails findByEmail(String email);

    User findByVerificarionCode(String verificationCode);
}
