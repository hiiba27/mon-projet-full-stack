package com.fst.rendement.entities;
import jakarta.persistence.*;

@Entity
@Table(name = "produits")
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nom;
    private String type;
    private float temps_standard;

    // Getters & Setters
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public float getTemps_standard() {
        return temps_standard;
    }
    public void setTemps_standard(float temps_standard) {
        this.temps_standard = temps_standard;
    }
}
