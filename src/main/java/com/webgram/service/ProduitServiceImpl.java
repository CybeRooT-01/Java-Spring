package com.webgram.service;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.webgram.entities.Produit;
import com.webgram.repository.ProduitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class ProduitServiceImpl implements IProduitService{
    private final ProduitRepository produitRepository;

    @Override
    public Produit saveProduct(Produit produit) {
        produit.setDatePublication(LocalDate.now());
        return produitRepository.save(produit);
    }

    @Override
    public Collection<Produit> getAllProducts() {
        return produitRepository.findAllByAndIsDeletedIsFalseOrderByIdDesc();
    }

    @Override
    public Produit updateProduct(Long id, Produit produit) {
        Produit updatedProduit = getProduitById(id);
        updatedProduit.setNom(produit.getNom());
        updatedProduit.setReference(produit.getReference());
        updatedProduit.setPrixUnitaire(produit.getPrixUnitaire());
        updatedProduit.setDescription(produit.getDescription());
        updatedProduit.setDatePublication(produit.getDatePublication());
        return produitRepository.save(updatedProduit);
    }

    @Override
    public void deleteProduct(Long id) {
        Produit deleteProduit = getProduitById(id);
        deleteProduit.setDeleted(true);
        produitRepository.save(deleteProduit);
    }

    private Produit getProduitById(Long id) {
        Produit existingProduit = produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Le produit sélectionné n'existe pas!"));
        return  existingProduit;
    }
}
