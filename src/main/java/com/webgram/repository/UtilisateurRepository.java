package com.webgram.repository;

import com.webgram.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    Optional<Utilisateur> findUtilisateurByEmail(String userName);

    @Query("SELECT u FROM Utilisateur u WHERE u.email =:email")
    Utilisateur findByEmail(String email);

    Optional<Utilisateur> findById(int id);
}
