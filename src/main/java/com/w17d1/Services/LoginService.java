package com.w17d1.Services;

import com.w17d1.Config.JWT;
import com.w17d1.Entities.Dipendente;
import com.w17d1.Exceptions.UnauthorizedException;
import com.w17d1.Payloads.LoginDipendentiDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private DipendentiService diS;

    @Autowired
    private JWT jwt;

    public String checkCredentialsAndGenerateToken(LoginDipendentiDTO body) {

        Dipendente found = this.diS.findByUsername(body.username());
        if (found.getPassword().equals(body.password())) {
            String accessToken = jwt.createToken(found);
            return accessToken;
        } else {
            throw new UnauthorizedException("Credenziali errate!");
        }
    }
}
