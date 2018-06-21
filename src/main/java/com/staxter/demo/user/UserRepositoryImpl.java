package com.staxter.demo.user;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class UserRepositoryImpl implements UserRepository{

    private static List<User> userList = new ArrayList<>();
    private static AtomicInteger id = new AtomicInteger(0);
    private final Object lock = new Object();

    @Override
    public User saveUser(User user) throws UserAlreadyExistsException {
        if (userWithUsernameAlreadyExist(user)) {
            throw new UserAlreadyExistsException();
        }

        synchronized (lock) {
            user.setId(String.valueOf(id.incrementAndGet()));
            userList.add(user);
        }
        return user;
    }

    @Override
    public void deleteAll() {
        userList = new ArrayList<>();
    }

    private boolean userWithUsernameAlreadyExist(User user) {
        return userList.stream().anyMatch(u -> u.getUserName().equals(user.getUserName()));
    }

}
