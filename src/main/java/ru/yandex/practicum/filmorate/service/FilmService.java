package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exseptions.NotFoundException;
import ru.yandex.practicum.filmorate.exseptions.ValidationException;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private InMemoryFilmStorage inMemoryFilmStorage;
    @Autowired
    public FilmService(InMemoryFilmStorage inMemoryFilmStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
    }
    public InMemoryFilmStorage getInMemoryFilmStorage() {
        return inMemoryFilmStorage;
    }

    public Collection<Film> getFavoriteFilms(long count) throws NotFoundException {
        if (count > 0) {
            Collection<Film> films = new ArrayList<>();
            Collection<Film> films2 = new ArrayList<>();
            if (count > 0) {
                Map<Integer, Film> resultWithNull = new HashMap<>();
                Map<Integer, Film> resultWithOutNull = new HashMap<>();
                Map<Integer, Film> resultSumLikesIsEmpty = new HashMap<>();
                Map<Integer, Film> resultSumLikes = new HashMap<>();
                for (Film film : inMemoryFilmStorage.getFilms().values()) {
                    if (film.getLikes() == null) {
                        if (resultWithNull.put((int) film.getId(), film) != null) {
                            throw new IllegalStateException("Duplicate key");
                        }
                    }
                }
                for (Film film : inMemoryFilmStorage.getFilms().values()) {
                    if (film.getLikes() != null) {
                        if (resultWithOutNull.put((int) film.getId(), film) != null) {
                            throw new IllegalStateException("Duplicate key");
                        }
                    }
                }

                for (Film film : resultWithOutNull.values()) {
                    if (film.getLikes().isEmpty()) {
                        if (resultSumLikesIsEmpty.put((int) film.getId(), film) != null) {
                            throw new IllegalStateException("Duplicate key");
                        }
                    }
                }
                for (Film film : resultWithOutNull.values()) {
                    if (!film.getLikes().isEmpty()) {
                        if (resultSumLikes.put((int) film.getId(), film) != null) {
                            throw new IllegalStateException("Duplicate key");
                        }
                    }
                }
                if ((resultWithNull.size() + resultSumLikesIsEmpty.size()) == inMemoryFilmStorage.getFilms().size()) {
                    films = resultWithNull.values().stream().collect(Collectors.toList());
                    films2 = resultSumLikesIsEmpty.values().stream().collect(Collectors.toList());
                    for (Film film : films) {
                        films2.add(film);
                    }
                    return films2;
                } else if (resultSumLikes.size() == 1) {
                    return resultSumLikes.values().stream().collect(Collectors.toList());

                } else if (resultSumLikes.size() > 1) {
                    return inMemoryFilmStorage.getFilms().values().stream().sorted(new FilmComporator())
                            .limit(count).collect(Collectors.toList());
                }
            } else {
                throw new NotFoundException("Ошибка списка популярных фильмов");
            }
        }
            return null;
    }

        static class FilmComporator implements Comparator<Film>{
            @Override
            public int compare(Film o1, Film o2) {
                return Integer.compare(o2.getLikes().size(),o1.getLikes().size());
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
            }else{
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
