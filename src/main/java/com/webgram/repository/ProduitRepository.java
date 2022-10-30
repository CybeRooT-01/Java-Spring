package com.webgram.repository;

import com.webgram.entities.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
    Collection<Produit> findAllByAndIsDeletedIsFalseOrderByIdDesc();
}
