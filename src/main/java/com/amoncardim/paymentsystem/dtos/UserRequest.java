package com.amoncardim.paymentsystem.dtos;

import com.amoncardim.paymentsystem.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequest(

        @NotNull(message = "O Nome não pode ser nulo.")
        @NotBlank(message = "O Nome não pode está em branco.")
        String name,

        @NotNull(message = "O Email não pode ser nulo.")
        @NotBlank(message = "O Email não pode está em branco.")
        @Email
        String email,

        @NotNull(message = "A Senha não pode ser nula.")
        @NotBlank(message = "A Senha não pode está em branco.")
        @Size(min = 8, message = "A senha deve conter mais de 8 caracteres.")
        String password) {

    public User toModel() {
        return new User(name, email, password);
    }
}
