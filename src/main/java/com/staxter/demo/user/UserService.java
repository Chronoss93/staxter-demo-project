package com.staxter.demo.user;

public interface UserService {
    User saveUser(CreateUserDto userDto) throws UserAlreadyExistsException;
}
