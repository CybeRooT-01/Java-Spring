package com.webgram.service;

import com.webgram.entities.Categorie;

import java.util.Collection;

public interface ICategorieService {
    Categorie saveCategorie(Categorie categorie);

    Collection<Categorie> getAllCategories();

    Categorie updateCategorie(Long id, Categorie categorie);

    void deleteCategorie(Long id);
}
