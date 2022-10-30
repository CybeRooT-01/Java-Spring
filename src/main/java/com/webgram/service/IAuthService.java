package com.webgram.service;

import com.webgram.entities.Utilisateur;

public interface IAuthService {
    Utilisateur getConnectedUser();
    Utilisateur findUtilisateurById(int id);
}
