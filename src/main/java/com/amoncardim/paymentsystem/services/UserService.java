package com.amoncardim.paymentsystem.services;

import com.amoncardim.paymentsystem.dtos.UserResponse;
import com.amoncardim.paymentsystem.entities.User;
import com.amoncardim.paymentsystem.repositories.UserRepository;
import com.amoncardim.paymentsystem.util.RandomString;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    public UserResponse registerUser(User user) throws MessagingException, UnsupportedEncodingException {
        if(userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("This email already exists.");
        } else {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);

            String randomCode = RandomString.generateRandomString(64);
            user.setVerificationCode(randomCode);
            user.setEnable(false);

            User saveUser = userRepository.save(user);

            UserResponse userResponse = new UserResponse(
                    saveUser.getId(),
                    saveUser.getName(),
                    saveUser.getEmail(),
                    saveUser.getPassword());

            mailService.sendVerificationEmail(user);

            return userResponse;
        }
    }

    public boolean verify(String verificationCode) {

        User user = userRepository.findByVerificarionCode(verificationCode);

        if (user == null || user.isEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnable(true);
            userRepository.save(user);

            return true;
        }
    }
}
