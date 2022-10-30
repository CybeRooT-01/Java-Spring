package com.webgram.service;

import java.util.List;

import com.webgram.entities.Utilisateur;
import com.webgram.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UtilisateurService implements IUtilisateurService {
    private final UtilisateurRepository utilisateurRepository;

    @Override
    public Utilisateur save(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    @Override
    public Utilisateur findByEmail(String email) {
        return utilisateurRepository.findByEmail(email);
    }

    @Override
    public List<Utilisateur> listeUtilisateurs() {
        return utilisateurRepository.findAll();
    }
}
