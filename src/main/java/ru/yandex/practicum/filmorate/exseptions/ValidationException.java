package ru.yandex.practicum.filmorate.exseptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ValidationException extends Exception {
        public ValidationException() {
        }
        public ValidationException(String message) {
            super(message);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
}
