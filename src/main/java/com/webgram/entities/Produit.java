package com.webgram.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;
import java.time.LocalDate;

@Entity
@Table(name = "produits")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Produit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 15)
    private String reference;
    @Column(nullable = false, length = 25)
    private String nom;
    @Column(nullable = false)
    private Double prixUnitaire;
    private byte[] photo;
    private LocalDate datePublication;
    @Column(length = 500)
    private String description;
    private boolean isDeleted = false;
    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;
}
