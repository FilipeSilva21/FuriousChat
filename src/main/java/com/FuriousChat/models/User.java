package com.FuriousChat.models;

import com.FuriousChat.models.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "tb_users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private String password;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthday;

    private String cpf;

    @Enumerated(EnumType.STRING)
    UserRole role;
}
