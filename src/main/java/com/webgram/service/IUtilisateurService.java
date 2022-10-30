package com.webgram.service;

import com.webgram.entities.Utilisateur;

import java.util.List;

public interface IUtilisateurService {
    Utilisateur save(Utilisateur utilisateur);

    Utilisateur findByEmail(String email);

    List<Utilisateur> listeUtilisateurs();
}
