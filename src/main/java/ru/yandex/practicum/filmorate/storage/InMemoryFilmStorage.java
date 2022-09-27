package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exseptions.NotFoundException;
import ru.yandex.practicum.filmorate.exseptions.ValidationException;
import ru.yandex.practicum.filmorate.models.Film;

import java.time.LocalDate;
import java.util.*;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private long id = 1;
    private Map<Long, Film> films = new HashMap<Long, Film>();
    LocalDate date = LocalDate.of(1895,12,28);

    public Map<Long, Film> getFilms() {
        return films;
    }

    @Override
    public List<Film> findAll() {
        List<Film> inventoryFilms = new ArrayList<>();
        for (Map.Entry<Long, Film> entry : films.entrySet()){
            inventoryFilms.add(entry.getValue());
        }
        return inventoryFilms;
    }

    @Override
    public Film addFilm(Film film) throws ValidationException{
        film.setId(id);

        if (films.containsKey(film.getId())) {
            throw new ValidationException("Tакого фильм уже существует");
        } else {
            if (film.getName().isEmpty()) {
                throw new ValidationException("Название фильма не указано");
            }
            if (film.getDescription().length() > 200) {
                throw new ValidationException("Опесание фильно вышло за границы 200 символов");
            }
            if (film.getReleaseDate().isBefore(date)) {
                throw new ValidationException("Дата релиза не может быть ранее 28 декабря 1895 года");
            }
            if (film.getDuration() < 0) {
                throw new ValidationException("Продолжительность фильма в минутах должна быль " +
                        "неотрицательной и больше 0");
            }
            films.put(film.getId(), film);
            id++;
        }
        return film;
    }

    @Override
    public void deleteFilm(long filmID) {
        if (films.containsKey(filmID)) {
            throw new NotFoundException("Таково пользователя нет");
        } else {
            films.remove(filmID);
            id--;
        }
    }
    @Override
    public Film updateFilm(Film film) throws ValidationException {
        if (!films.containsKey(film.getId())) {
            throw new NotFoundException("Нет такого фильма");
        }else {
            if (film.getName().isEmpty()) {
                throw new ValidationException("Название фильма не указано");
            }
            if (film.getDescription().length() > 200) {
                throw new ValidationException("Опесание фильно вышло за границы 200 символов");
            }
            if (film.getReleaseDate().isBefore(date)) {
                throw new ValidationException("Дата релиза не может быть ранее 28 декабря 1895 года");
            }
            if (film.getDuration() < 0) {
                throw new ValidationException("Продолжительность фильма в минутах должна быль " +
                        "неотрицательной и больше 0");
            }
            films.put(film.getId(), film);
            return film;
        }
    }
}
