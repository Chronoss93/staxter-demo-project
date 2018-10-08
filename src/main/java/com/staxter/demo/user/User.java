package com.staxter.demo.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @JsonIgnore
    private String id;
    private String firstName;
    private String lastName;
    private String userName;
    @JsonIgnore//I strongly recommend to not store password as plain text in DB. Only hash. It is security best practices
    private String plainTextPassword;
    @JsonIgnore
    private String hashedPassword;

    public User(String firstName, String lastName, String userName, String hashedPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.hashedPassword = hashedPassword;
    }
}
