package ru.yandex.practicum.filmorate.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exseptions.NotFoundException;
import ru.yandex.practicum.filmorate.exseptions.ValidationException;
import ru.yandex.practicum.filmorate.models.ErrorResponse;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@RestControllerAdvice(basePackages = "ru.yandex.practicum.controllers")
@RestControllerAdvice(assignableTypes = {UserController.class, FilmController.class, InMemoryUserStorage.class,
        InMemoryFilmStorage.class, UserService.class,FilmController.class})
public class ErrorHander {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerBadValidation(final ValidationException e){
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerUserNotFound(final NotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }

//    @ExceptionHandler(NotFoundException.class)
//    public void handleNotFound(HttpServletResponse response) throws IOException {
//        response.sendError(HttpStatus.NOT_FOUND.value());
//    }


}
