package ru.yandex.practicum.filmorate.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private long id;

    @NotBlank(message = "E-mail не введен")
    @Email(message = "E-mail должен содержать символ @")
    private String email;

    @NotBlank(message = "Логин не введен")
    private String login;

    @Null
    private String name = null;

    @Future
    private LocalDate birthday;

    @Null
    private Set<Long> friends;

}
