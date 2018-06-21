package com.staxter.demo.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/userservice")
public class UserController {

    private UserService userService;

    @PostMapping(value = "/register")
    public ResponseEntity<?> consumeEvent(@Valid @RequestBody CreateUserDto userDto) throws UserAlreadyExistsException {
        log.info("register user with username={}", userDto.getUsername());
        log.debug("Input user DTO:\n{}", userDto);
        User user = userService.saveUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

}

