package com.w17d1.Payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record NewDipendenteDTO(@NotEmpty(message = "Username è un campo obbligatorio!")
                               @Size(min = 4, message = "Inserisci un Username di almeno 4 caratteri!")
                               String username,
                               @NotEmpty(message = "Il nome proprio è obbligatorio!")
                               @Size(min = 2, max = 40, message = "Il nome proprio deve essere compreso tra 2 e 40 caratteri!")
                               String nome,
                               @NotEmpty(message = "Il cognome  è obbligatorio!")
                               @Size(min = 2, max = 40, message = "Il cognome deve essere compreso tra 2 e 40 caratteri!")
                               String cognome,
                               @NotEmpty(message = "L'email è un campo obbligatorio!")
                               @Email(message = "L'email inserita non è un'email valida")
                               String email,
                               @NotEmpty(message = "Inserisci una password")
                               String password) {
}
