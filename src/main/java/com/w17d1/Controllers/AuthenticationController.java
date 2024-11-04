package com.w17d1.Controllers;

import com.w17d1.Entities.Dipendente;
import com.w17d1.Exceptions.BadRequestException;
import com.w17d1.Payloads.LoginDipendentiDTO;
import com.w17d1.Payloads.LoginResponseDTO;
import com.w17d1.Payloads.NewDipendenteDTO;
import com.w17d1.Services.DipendentiService;
import com.w17d1.Services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private LoginService loS;
    @Autowired
    private DipendentiService diS;

    //2. POST http://localhost:3001/auth/login (+ req.body) --> 201
    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginDipendentiDTO body) {
        return new LoginResponseDTO(this.loS.checkCredentialsAndGenerateToken(body));
    }

    // 2. POST http://localhost:3001/auth/register
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Dipendente save(@RequestBody @Validated NewDipendenteDTO body, BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }

        return this.diS.save(body);
    }
}
