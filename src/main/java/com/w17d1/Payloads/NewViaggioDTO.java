package com.w17d1.Payloads;

import com.w17d1.Entities.StatoViaggio;

import java.time.LocalDate;

public record NewViaggioDTO(
        String destinazione,
        LocalDate dataInizio,
        LocalDate dataFine,
        StatoViaggio statoViaggio) {
}
