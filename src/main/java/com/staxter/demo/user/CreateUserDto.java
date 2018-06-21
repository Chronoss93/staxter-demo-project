package com.staxter.demo.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@ToString
class CreateUserDto {
    @NotNull(message = "first name must not be null")
    @NotEmpty(message = "first name must not be empty string")
    private String firstName;
    @NotNull(message = "last name must not be null")
    @NotEmpty(message = "last name must not be empty string")
    private String lastName;
    @NotNull(message = "username must not be null")
    @NotEmpty(message = "username must not be empty string")
    @Length(min = 6, max = 10, message = "username length must be between 6 and 10")
    private String username;
    @NotNull(message = "password must not be null")
    @NotEmpty(message = "password must not be empty string")
    @Length(min = 6, max = 20, message = "password length must be between 6 and 20")
    private String password;

    public User convertToUser(PasswordEncoder passwordEncoder){
        String encodedPwd = passwordEncoder.encode(password);
        return new User(firstName, lastName, username, encodedPwd);
    }
}
