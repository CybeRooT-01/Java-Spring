package com.webgram.service;

import com.webgram.entities.Profil;
import com.webgram.repository.ProfilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfilService implements IProfilService {
    private final ProfilRepository profilRepository;

    @Override
    public Profil save(Profil profil) {
        return profilRepository.save(profil);
    }
}
