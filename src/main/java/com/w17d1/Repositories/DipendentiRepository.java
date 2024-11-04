package com.w17d1.Repositories;

import com.w17d1.Entities.Dipendente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DipendentiRepository extends JpaRepository<Dipendente, Long> {
    Optional<Object> findByUsername(@NotEmpty(message = "Username è un campo obbligatorio!") @Size(min = 4, message = "Inserisci un Username di almeno 4 caratteri!") String username);

    Optional<Object> findByEmail(@NotEmpty(message = "L'email è un campo obbligatorio!") @Email(message = "L'email inserita non è un'email valida") String email);
}
