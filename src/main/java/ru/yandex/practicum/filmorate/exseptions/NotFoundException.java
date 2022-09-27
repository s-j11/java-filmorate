package ru.yandex.practicum.filmorate.exseptions;
public class NotFoundException extends RuntimeException{
    public NotFoundException() {
        }
    public NotFoundException(String message) {
            super(message);
        }
}
