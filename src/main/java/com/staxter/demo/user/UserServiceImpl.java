package com.staxter.demo.user;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(CreateUserDto userDto) throws UserAlreadyExistsException {
        User user = userDto.convertToUser(passwordEncoder);
        return userRepository.saveUser(user);
    }
}
