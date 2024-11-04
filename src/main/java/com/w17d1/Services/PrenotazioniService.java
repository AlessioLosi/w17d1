package com.w17d1.Services;

import com.w17d1.Entities.Dipendente;
import com.w17d1.Entities.Prenotazione;
import com.w17d1.Entities.Viaggio;
import com.w17d1.Exceptions.NotFoundException;
import com.w17d1.Payloads.NewPrenotazioneDTO;
import com.w17d1.Repositories.DipendentiRepository;
import com.w17d1.Repositories.PrenotazioniRepository;
import com.w17d1.Repositories.ViaggioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PrenotazioniService {
    @Autowired
    private PrenotazioniRepository prR;
    @Autowired
    private ViaggioRepository viR;
    @Autowired
    private DipendentiRepository diR;

    public Prenotazione save(NewPrenotazioneDTO body, Long dipendente_Id, Long viaggio_Id) throws Exception {

        Dipendente dipendente = diR.findById(dipendente_Id)
                .orElseThrow(() -> new NotFoundException("Dipendente  non trovato"));
        Viaggio viaggio = viR.findById(viaggio_Id).orElseThrow(() -> new NotFoundException("Viaggio  non trovato"));

        Prenotazione newPrenotazione = new Prenotazione(dipendente, viaggio, body.note(), body.data_di_richiesta());

        return this.prR.save(newPrenotazione);

    }

    // public Prenotazione check(Dipendente dipendente, Viaggio viaggio) throws Exception {
    //     if (dipendente == null || viaggio == null) throw new Exception();
    //     return check(dipendente, viaggio);
    // }

    public Prenotazione findById(Long prenotazioneId) {
        return this.prR.findById(prenotazioneId).orElseThrow(() -> new NotFoundException("non trovata la prenotazione con id" + prenotazioneId));
    }


    public void findByIdAndDelete(Long prenotazioneId) {
        Prenotazione found = this.findById(prenotazioneId);
        this.prR.delete(found);
    }


    public Page<Prenotazione> findAll(int page, int size, String sortBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.prR.findAll(pageable);
    }

    //public NewPrenotazioneDTO savePreno(Long viaggio, Long dipendente, NewPrenotazioneDTO NewPrenotazioneDTO) {
    //    try {
    //       if (FindPrenotazioni(dipendente, viaggio).isEmpty()) {
    //
    //           prR.save(NewPrenotazioneDTO.getClass());
    //           System.out.println("Prenotazione effettuata");
    //       } else {
    //           throw new BadRequestException("Hai già prenotato per oggi");
    //       }
    //   } catch (BadRequestException e) {
    //       e.getMessage();
    //   }
    //   return NewPrenotazioneDTO;
    // }


    //public List<Prenotazione> FindPrenotazioni(Long dipendente, Long viaggio) {
    //   try {
    //       prR.findAll().stream().filter(prenotazioni -> prenotazioni.getDipendente_id().equals(dipendente) && prenotazioni.getViaggio_id().getId().equals(viaggio)).collect(Collectors.toList());
    //   } catch (Exception e) {
    //       e.printStackTrace();
    //        return Collections.emptyList();
    //    }
    //    return List.of();
    //}

}
