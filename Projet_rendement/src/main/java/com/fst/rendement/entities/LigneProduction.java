package com.fst.rendement.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "lignes_production")
public class LigneProduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nom;
    private int capacite_max;
    @ManyToOne
    private User chef; 
    
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

    public int getCapacite_max() {
        return capacite_max;
    }
    public void setCapacite_max(int capacite_max) {
        this.capacite_max = capacite_max;
    }

    public User getChef(){
        return chef;
    }
    public void setChef(User chef){
        this.chef = chef;
    }
}


