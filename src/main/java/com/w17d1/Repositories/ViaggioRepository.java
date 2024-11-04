package com.w17d1.Repositories;

import com.w17d1.Entities.Viaggio;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ViaggioRepository extends JpaRepository<Viaggio, Long> {
    Optional<Object> findByDestinazione(@NotEmpty(message = "destinazione è un campo obbligatorio!") @Size(min = 4, message = "Inserisci un Username di almeno 4 caratteri!") String username);
}
