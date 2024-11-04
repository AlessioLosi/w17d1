package com.w17d1.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "prenotazioni")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Prenotazione {
    @ManyToOne
    @JoinColumn(name = "dipendente_id")
    private Dipendente dipendente_id;
    @ManyToOne
    @JoinColumn(name = "viaggio_id")
    private Viaggio viaggio_id;
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private Long id;
    private String note;
    private LocalDate datarichiesta;

    public Prenotazione(Dipendente dipendente_id, Viaggio viaggio_id, String note, LocalDate datarichiesta) {
        this.dipendente_id = dipendente_id;
        this.viaggio_id = viaggio_id;
        this.note = note;
        this.datarichiesta = datarichiesta;
    }
}
