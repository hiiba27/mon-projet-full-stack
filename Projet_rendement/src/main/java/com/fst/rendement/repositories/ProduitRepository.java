package com.fst.rendement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fst.rendement.entities.Produit;

public interface ProduitRepository extends JpaRepository<Produit, Integer> {
}

