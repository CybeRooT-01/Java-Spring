package com.webgram.service;

import com.webgram.entities.Categorie;
import com.webgram.repository.CategorieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class CategorieServiceImpl implements ICategorieService {
    private final CategorieRepository categorieRepository;

    @Override
    public Categorie saveCategorie(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    @Override
    public Collection<Categorie> getAllCategories() {
        return categorieRepository.getAllCategories();
    }

    @Override
    public Categorie updateCategorie(Long id, Categorie categorie) {
        Categorie recuperationCategorie = getCategorieById(id);
        recuperationCategorie.setCode(categorie.getCode());
        recuperationCategorie.setNom(categorie.getNom());
        recuperationCategorie.setDescription(categorie.getDescription());
        return categorieRepository.save(recuperationCategorie);
    }

    @Override
    public void deleteCategorie(Long id) {
        Categorie recuperationCategorie = getCategorieById(id);
        categorieRepository.delete(recuperationCategorie);
    }

    private Categorie getCategorieById(Long id) {
        Categorie existingCategorie = categorieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("la catégorie sélectionnée n'existe pas!"));
        return existingCategorie;
    }
}
