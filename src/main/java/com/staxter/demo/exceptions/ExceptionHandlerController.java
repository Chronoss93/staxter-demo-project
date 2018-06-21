package com.staxter.demo.exceptions;

import com.staxter.demo.user.UserAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserAlreadyExist(final UserAlreadyExistsException ex) {
        ApiError error = new ApiError("USER_ALREADY_EXIST", Collections.singletonList("User with such username already exist"));
        log.debug("Couldn't create user: %s", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleDtoValidation(MethodArgumentNotValidException ex) {
        ApiError apiError = new ApiError("DTO_VALIDATION_ERROR", extractValidationErrors(ex));
        log.debug("Validation error: %s", ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    private List<String> extractValidationErrors(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAll(Exception ex) {
        ApiError apiError = new ApiError("INTERNAL_API_ERROR", Collections.singletonList("something went wrong.Details: http://kvartirakrasivo.ru/404/index.php"));
        log.debug("Internal API error: %s", ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
