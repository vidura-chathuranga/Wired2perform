package com.vidura.Wired2perform.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponse <T>{
    private String message;
    private T data;

    public static <T> GenericResponse<T> success(String message, T data) {
        return new GenericResponse<>(message, data);
    }

    public static <T> GenericResponse<T> failure(String message) {
        return new GenericResponse<>(message, null);
    }
}
