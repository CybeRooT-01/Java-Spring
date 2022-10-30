package com.webgram.service;

import com.webgram.entities.Utilisateur;
import com.webgram.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UtilisateurRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Utilisateur> userSearch = Optional.ofNullable(repository.findUtilisateurByEmail(username).
                orElse(null));

        return userSearch.map(utilisateur -> {
            List<GrantedAuthority> profils = new ArrayList<>();
            profils.add(new SimpleGrantedAuthority(utilisateur.getProfil().getLibelle()));
            return new User(username, utilisateur.getPassword(), profils);
        }).orElseThrow(() -> new UsernameNotFoundException("L'utilisateur avec l'email " + username + " n'existe pas !"));
    }
}
