package com.w17d1.Controllers;

import com.w17d1.Entities.Dipendente;
import com.w17d1.Exceptions.BadRequestException;
import com.w17d1.Payloads.NewDipendenteDTO;
import com.w17d1.Services.DipendentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/dipendenti")
public class DipendentiController {
    @Autowired
    private DipendentiService dipendentiService;

    // 1. GET http://localhost:3001/dipendenti
    @GetMapping
    public Page<Dipendente> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "id") String sortBy) {

        return this.dipendentiService.findAll(page, size, sortBy);
    }

    // 2. POST http://localhost:3001/dipendenti (+ req.body) --> 201
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Dipendente save(@RequestBody @Validated NewDipendenteDTO body, BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }

        return this.dipendentiService.save(body);
    }


    // 3. GET http://localhost:3001/dipendenti/{dipendentiId}
    @GetMapping("/{dipendentiId}")
    public Dipendente findById(@PathVariable Long dipendentiId) {
        return this.dipendentiService.findById(dipendentiId);
    }


    // 4. PUT http://localhost:3001/dipendenti/{dipententiId} (+ req.body)
    @PutMapping("/{dipendentiId}")
    public Dipendente findByIdAndUpdate(@PathVariable Long dipendentiId, @RequestBody @Validated NewDipendenteDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            validationResult.getAllErrors().forEach(System.out::println);
            throw new BadRequestException("Ci sono stati errori nel payload!");
        }
        return this.dipendentiService.findByIdAndUpdate(dipendentiId, body);
    }


    // 5. DELETE http://localhost:3001/dipendenti/{dipendentiId} --> 204
    @DeleteMapping("/{dipendentiId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable Long dipendentiId) {
        this.dipendentiService.findByIdAndDelete(dipendentiId);
    }

    // 6. PATCH http://localhost:3001/dipendenti/{dipendentiId}/avatar
    @PatchMapping("/{dipendentiId}/avatar")
    public String uploadAvatar(@RequestParam("avatar") MultipartFile file, Long dipendentiId) {

        return this.dipendentiService.uploadAvatar(file, dipendentiId);
    }
}
