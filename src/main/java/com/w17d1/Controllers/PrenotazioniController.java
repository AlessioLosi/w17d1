package com.w17d1.Controllers;

import com.w17d1.Entities.Prenotazione;
import com.w17d1.Exceptions.BadRequestException;
import com.w17d1.Payloads.NewPrenotazioneDTO;
import com.w17d1.Services.PrenotazioniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioniController {
    @Autowired
    private PrenotazioniService prenotazioniService;

    // 1. GET http://localhost:3001/prenotazioni
    @GetMapping
    public Page<Prenotazione> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(defaultValue = "id") String sortBy) {

        return this.prenotazioniService.findAll(page, size, sortBy);
    }

    // 2. POST http://localhost:3001/prenotazioni (+ req.body) --> 201
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Prenotazione save(@RequestBody @Validated NewPrenotazioneDTO body, BindingResult validationResult) throws Exception {

        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }

        return this.prenotazioniService.save(body, body.dipendente_Id(), body.viaggio_Id());
    }


    // 3. GET http://localhost:3001/prenotazioni/{prenotazioniId}
    @GetMapping("/{prenotazioniId}")
    Prenotazione findById(@PathVariable Long prenotazioniId) {
        return this.prenotazioniService.findById(prenotazioniId);
    }


    //4. PUT http://localhost:3001/prenotazioni/{dipententiId} (+ req.body)
    //@PutMapping("/{prenotazioniId}")
    // Prenotazione findByIdAndUpdate(@PathVariable Long prenotazioniId, @RequestBody @Validated NewPrenotazioneDTO body, BindingResult validationResult) {
    //    if (validationResult.hasErrors()) {
    //         validationResult.getAllErrors().forEach(System.out::println);
    //         throw new BadRequestException("Ci sono stati errori nel payload!");
    //   }
//     return this.prenotazioniService.findByIdAndUpdate(prenotazioniId, body);
    //  }
//

    // 5. DELETE http://localhost:3001/prenotazioni/{prenotazioniId} --> 204
    @DeleteMapping("/{prenotazioniId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable Long prenotazioniId) {
        this.prenotazioniService.findByIdAndDelete(prenotazioniId);
    }
}
