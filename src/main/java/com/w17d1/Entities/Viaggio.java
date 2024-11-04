package com.w17d1.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "viaggio")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Viaggio {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private Long id;
    private String destinazione;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private StatoViaggio statoViaggio;

    public Viaggio(String destinazione, LocalDate dataInizio, LocalDate dataFine, StatoViaggio statoViaggio) {
        this.destinazione = destinazione;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.statoViaggio = statoViaggio;
    }
}
