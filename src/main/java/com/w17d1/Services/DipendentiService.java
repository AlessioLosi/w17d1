package com.w17d1.Services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.w17d1.Entities.Dipendente;
import com.w17d1.Exceptions.BadRequestException;
import com.w17d1.Exceptions.NotFoundException;
import com.w17d1.Payloads.NewDipendenteDTO;
import com.w17d1.Repositories.DipendentiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class DipendentiService {
    @Autowired
    private DipendentiRepository diR;
    @Autowired
    private Cloudinary cloudinary;

    public Dipendente save(NewDipendenteDTO body) {
        this.diR.findByUsername(body.username()).ifPresent(
                dipendente -> {
                    throw new BadRequestException("Username" + body.username() + " già in uso!");
                }
        );

        Dipendente newDipentente = new Dipendente(body.username(), body.nome(), body.cognome(), body.email(),
                "https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome());


        return this.diR.save(newDipentente);
    }

    public Dipendente findById(Long dipendenteId) {
        return this.diR.findById(dipendenteId).orElseThrow(() -> new NotFoundException("non trovato un dipendente con id" + dipendenteId));
    }

    public Dipendente findByIdAndUpdate(Long dipendenteId, NewDipendenteDTO body) {

        Dipendente found = this.findById(dipendenteId);

        if (!found.getEmail().equals(body.email())) {
            this.diR.findByEmail(body.email()).ifPresent(
                    dipendente -> {
                        throw new BadRequestException("Email " + body.email() + " già in uso!");
                    }
            );
        }


        found.setNome(body.nome());
        found.setCognome(body.cognome());
        found.setEmail(body.email());
        found.setUsername(body.username());
        found.setAvatarURL("https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome());

        return this.diR.save(found);
    }

    public void findByIdAndDelete(Long dipendenteId) {
        Dipendente found = this.findById(dipendenteId);
        this.diR.delete(found);
    }


    public Page<Dipendente> findAll(int page, int size, String sortBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.diR.findAll(pageable);
    }

    public String uploadAvatar(MultipartFile file, Long id_dipendente) {
        Dipendente dipendente = this.findById(id_dipendente);
        String url = null;
        try {
            url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        } catch (IOException e) {
            throw new BadRequestException("Ci sono stati problemi con l'upload del file!");
        }
        dipendente.setAvatarURL(url);
        this.diR.save(dipendente);

        return url;


    }
}
