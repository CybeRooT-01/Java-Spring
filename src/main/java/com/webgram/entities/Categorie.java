package com.webgram.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Categorie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name ="codes", nullable = false, length = 5)
    private String code;
    @Column(length = 50, nullable = false)
    private String nom;
    @Column(length = 500)
    private String description;
    private boolean isDeleted = false;
    @OneToMany(mappedBy = "categorie")
    Collection<Produit> produits;
}
