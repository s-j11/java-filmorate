package ru.yandex.practicum.filmorate.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exseptions.ValidationException;
import ru.yandex.practicum.filmorate.models.ErrorResponse;

@ControllerAdvice
public class ErrorHander {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handlerBadValidation(final ValidationException e){
        return new ErrorResponse(e.getMessage());
    }
}
