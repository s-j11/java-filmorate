package ru.yandex.practicum.filmorate.exseptions;
public class ValidationException extends Exception {
    public ValidationException() {
    }
    public ValidationException(String message) {
        super(message);
    }
}