package com.staxter.demo.user;

public interface UserRepository {
    User saveUser(User user) throws UserAlreadyExistsException;

    void deleteAll();
}
