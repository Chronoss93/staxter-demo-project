package com.staxter.demo.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor // for objectMapper.readValue(...)
public class ApiError {

    private String code;
    private List<String> description;
}
