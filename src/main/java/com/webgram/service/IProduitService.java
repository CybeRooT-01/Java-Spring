package com.webgram.service;

import com.webgram.entities.Produit;

import java.util.Collection;

public interface IProduitService {
   Produit saveProduct(Produit produit);

   Collection<Produit> getAllProducts();

   Produit updateProduct(Long id, Produit produit);

   void deleteProduct(Long id);
}
