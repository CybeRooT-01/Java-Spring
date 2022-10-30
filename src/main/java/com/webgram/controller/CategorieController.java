package com.webgram.controller;

import com.webgram.entities.Categorie;
import com.webgram.service.CategorieServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/webgram/v1")
public class CategorieController {
    private final CategorieServiceImpl categorieService;

    @PostMapping("/saveCategorie")
    public ResponseEntity<Categorie> saveCategorie(@RequestBody Categorie categorie) {
        Categorie savedCategorie = categorieService.saveCategorie(categorie);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategorie);
    }

    @GetMapping("/listeCategorie")
    public ResponseEntity<Collection<Categorie>> getAllCategorie() {
        return ResponseEntity.ok(categorieService.getAllCategories());
    }

    @PutMapping("/updateCategorie/{id}")
    public ResponseEntity<Categorie> updateCategorie(@PathVariable Long id, @RequestBody Categorie categorie) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(categorieService.updateCategorie(id, categorie));
    }

    @DeleteMapping("/deleteCategorie/{id}")
    public ResponseEntity<Object> deletedCategorie(@PathVariable Long id) {
        categorieService.deleteCategorie(id);
        return ResponseEntity.noContent().build();
    }
}
