package com.w17d1.Services;

import com.w17d1.Entities.StatoViaggio;
import com.w17d1.Entities.Viaggio;
import com.w17d1.Exceptions.NotFoundException;
import com.w17d1.Payloads.NewViaggioDTO;
import com.w17d1.Repositories.ViaggioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ViaggioService {
    @Autowired
    private ViaggioRepository viR;

    public Viaggio save(NewViaggioDTO body) {


        Viaggio newViaggio = new Viaggio(body.destinazione(), body.dataInizio(), body.dataFine(), body.statoViaggio());


        return this.viR.save(newViaggio);
    }

    public Viaggio findById(Long viaggioId) {
        return this.viR.findById(viaggioId).orElseThrow(() -> new NotFoundException("non trovato il viaggio con id" + viaggioId));
    }


    public void findByIdAndDelete(Long viaggioId) {
        Viaggio found = this.findById(viaggioId);
        this.viR.delete(found);
    }


    public Page<Viaggio> findAll(int page, int size, String sortBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.viR.findAll(pageable);
    }

    public Viaggio updateStato(Long viaggioId, StatoViaggio statoViaggio) {
        Viaggio found = findById(viaggioId);
        if (found != null) {

            found.setStatoViaggio(statoViaggio);
            return viR.save(found);
        } else {
            System.out.println(" il viaggio non esiste");
            return null;
        }
    }
}
