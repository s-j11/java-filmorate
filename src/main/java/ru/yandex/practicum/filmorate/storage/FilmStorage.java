package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exseptions.ValidationException;
import ru.yandex.practicum.filmorate.models.Film;

import java.util.List;

public interface FilmStorage {

    public List<Film> findAll();
    public Film addFilm(Film film) throws ValidationException;

    public void deleteFilm(long filmID);

    public Film updateFilm(Film film) throws ValidationException;
}
