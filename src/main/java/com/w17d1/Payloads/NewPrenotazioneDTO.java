package com.w17d1.Payloads;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record NewPrenotazioneDTO(
        String note,
        LocalDate data_di_richiesta,

        @NotNull(message = "L'ID del dipendente è obbligatorio")
        Long dipendente_Id,

        @NotNull(message = "L'ID del viaggio è obbligatorio")
        Long viaggio_Id) {
}
