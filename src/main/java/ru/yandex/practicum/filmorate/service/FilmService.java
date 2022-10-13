package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.time.LocalDate;
import java.util.*;

@Service
public class FilmService {
    private InMemoryFilmStorage inMemoryFilmStorage;
    private Map<Long, Film> films = new HashMap<Long, Film>();
    private LocalDate date = LocalDate.of(1895, 12, 28);

    @Autowired
    public FilmService(InMemoryFilmStorage inMemoryFilmStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
    }

    public InMemoryFilmStorage getInMemoryFilmStorage() {
        return inMemoryFilmStorage;
    }

    public List<Film> findAllFilms() {
        return inMemoryFilmStorage.findAll();
    }

    public Film addFilm(Film film) throws ValidationException {
        films = inMemoryFilmStorage.getFilms();
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
            return inMemoryFilmStorage.addFilm(film);
        }
    }

    public Film updateFilm(Film film) throws ValidationException {
        films = inMemoryFilmStorage.getFilms();
        if (!films.containsKey(film.getId())) {
            throw new NotFoundException("Нет такого фильма");
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
        }
        return inMemoryFilmStorage.updateFilm(film);
    }

    public void deleteFilm(long filmID) {
        inMemoryFilmStorage.deleteFilm(filmID);
    }

    public Collection<Film> getFavoriteFilms(long count) throws NotFoundException {
        return inMemoryFilmStorage.getFavoritFilms(count);
    }

    static class FilmComporator implements Comparator<Film> {
        @Override
        public int compare(Film o1, Film o2) {
            return Integer.compare(o2.getLikes().size(), o1.getLikes().size());
        }
    }

    public void addLike(Long filmId, Long userId) throws ValidationException {
        if (!inMemoryFilmStorage.getFilms().containsKey(filmId)) {
            throw new NotFoundException("Нет такого фильма");
        } else {
            Set<Long> likes = new HashSet<>();
            Film film = inMemoryFilmStorage.getFilms().get(filmId);
            if (film.getLikes() == null) {
                likes.add(userId);
                film.setLikes(likes);
                inMemoryFilmStorage.updateFilm(film);
            } else {
                likes = inMemoryFilmStorage.getFilms().get(filmId).getLikes();
                likes.add(userId);
                film.setLikes(likes);
                inMemoryFilmStorage.updateFilm(film);
            }
        }
    }

    public void deleteLike(Long filmId, Long userId) throws NotFoundException, ValidationException {
        if (!inMemoryFilmStorage.getFilms().containsKey(filmId)) {
            throw new NotFoundException("Нет такого фильма");
        } else {
            Film film = inMemoryFilmStorage.getFilms().get(filmId);
            Set<Long> likes = new HashSet<>();
            if (film.getLikes() == null) {
                throw new NotFoundException("Нет такого пользователя");
            } else {
                likes = film.getLikes();
                likes.remove(userId);
                film.setLikes(likes);
                inMemoryFilmStorage.updateFilm(film);
            }
        }
    }

    public Film getFilmByID(long id) throws NotFoundException {
        if (!inMemoryFilmStorage.getFilms().containsKey(id)) {
            throw new NotFoundException("Нет такого фильма");
        } else {
            Film film = inMemoryFilmStorage.getFilms().get(id);
            return film;
        }
    }
}
