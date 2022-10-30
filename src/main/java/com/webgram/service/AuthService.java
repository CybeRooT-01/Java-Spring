package com.webgram.service;

import com.webgram.entities.Utilisateur;
import com.webgram.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService implements IAuthService, AuthenticationProvider {
    private final UtilisateurRepository utilisateurRepository;

    @Override
    public Utilisateur getConnectedUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            return utilisateurRepository.findUtilisateurByEmail(email).orElse(null);
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Utilisateur findUtilisateurById(int id) {
        return utilisateurRepository.findById(id).orElse(null);
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

}
