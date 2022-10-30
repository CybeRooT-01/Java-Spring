package com.webgram.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Utilisateur implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;

    private String prenom;

    private Boolean Statut = true;

    private String email;

    private String telephone;

    @CreationTimestamp
    private Date dateCreation;

    private String login;

    private String password;

    @Column(nullable = false)
    private boolean newConnexion = true;

    @ManyToOne
    @JoinColumn(name = "profil_id")
    private Profil profil;
}
