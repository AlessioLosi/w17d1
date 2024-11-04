package com.w17d1.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;


@Entity
@Table(name = "dipendente")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Dipendente {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private Long id;
    private String username;
    private String nome;
    private String cognome;
    private String email;
    private String avatarURL;

    public Dipendente(String username, String nome, String cognome, String email, String avatarURL) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.avatarURL = avatarURL;
    }
}
