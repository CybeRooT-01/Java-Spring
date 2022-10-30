package com.webgram.repository;

import com.webgram.entities.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Collection;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    @Query("select c from Categorie c where c.isDeleted = false")
    Collection<Categorie> getAllCategories();
}
