package com.webgram.controller;

import com.webgram.entities.Produit;
import com.webgram.service.ProduitServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/webgram/v1")
public class ProduitController {
    private final ProduitServiceImpl produitService;

    @PostMapping(value = "/saveProduit", headers = {"content-type = multipart/form-data"})
    public ResponseEntity<Produit> saveProduit(@Nullable @RequestParam("photo") MultipartFile photo, @RequestBody Produit produit) throws IOException {
        if(photo != null) {
            produit.setPhoto(photo.getBytes());
        } else {
            return null;
        }
        Produit savedProduit = produitService.saveProduct(produit);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduit);
    }

    @GetMapping("/listeProduit")
    public ResponseEntity<Collection<Produit>> getAllProduit() {
        return ResponseEntity.ok(produitService.getAllProducts());
    }

    @PutMapping("/updateProduit/{id}")
    public ResponseEntity<Produit> updateProduit(@PathVariable Long id, @RequestBody Produit Produit) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(produitService.updateProduct(id, Produit));
    }

    @DeleteMapping("/deleteProduit/{id}")
    public ResponseEntity<Object> deletedProduit(@PathVariable Long id) {
        produitService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
