package com.w17d1.Controllers;

import com.w17d1.Entities.Viaggio;
import com.w17d1.Exceptions.BadRequestException;
import com.w17d1.Payloads.NewViaggioDTO;
import com.w17d1.Services.ViaggioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/viaggi")
public class ViaggiController {
    @Autowired
    private ViaggioService viaggioService;

    // 1. GET http://localhost:3001/viaggi
    @GetMapping
    public Page<Viaggio> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "id") String sortBy) {

        return this.viaggioService.findAll(page, size, sortBy);
    }

    // 2. POST http://localhost:3001/viaggi (+ req.body) --> 201
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Viaggio save(@RequestBody @Validated NewViaggioDTO body, BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }

        return this.viaggioService.save(body);
    }


    // 3. GET http://localhost:3001/viaggi/{viaggiId}
    @GetMapping("/{viaggiId}")
    public Viaggio findById(@PathVariable Long viaggiId) {
        return this.viaggioService.findById(viaggiId);
    }


    // 4. PUT http://localhost:3001/viaggi/{viaggiId} (+ req.body)
    @PutMapping("/{viaggiId}")
    public Viaggio updateStato(@PathVariable Long viaggiId, @RequestBody @Validated NewViaggioDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            validationResult.getAllErrors().forEach(System.out::println);
            throw new BadRequestException("Ci sono stati errori nel payload!");
        }
        return this.viaggioService.updateStato(viaggiId, body.statoViaggio());
    }


    // 5. DELETE http://localhost:3001/viaggi/{viaggiId} --> 204
    @DeleteMapping("/{viaggiId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable Long dipendentiId) {
        this.viaggioService.findByIdAndDelete(dipendentiId);
    }
}
