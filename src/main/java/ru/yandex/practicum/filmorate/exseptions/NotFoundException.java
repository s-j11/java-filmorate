package ru.yandex.practicum.filmorate.exseptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends RuntimeException{
    public NotFoundException() {
        }
    public NotFoundException(String message) {
            super(message);
        }
}
