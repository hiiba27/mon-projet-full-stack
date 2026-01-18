package com.fst.rendement.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "machines")
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nom;
    private String etat; // "En marche", "Arrêtée", "Panne" ou "Maintenance"
    private float taux_utilisation;

    private int heures_fonctionnement;
    private int production_totale;
    private LocalDate derniere_maintenance;

    @ManyToOne
    private LigneProduction ligne;

    // --- Getters / Setters ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }

    public float getTaux_utilisation() { return taux_utilisation; }
    public void setTaux_utilisation(float taux_utilisation) { this.taux_utilisation = taux_utilisation; }

    public int getHeures_fonctionnement() { return heures_fonctionnement; }
    public void setHeures_fonctionnement(int heures_fonctionnement) { this.heures_fonctionnement = heures_fonctionnement; }

    public int getProduction_totale() { return production_totale; }
    public void setProduction_totale(int production_totale) { this.production_totale = production_totale; }

    public LocalDate getDerniere_maintenance() { return derniere_maintenance; }
    public void setDerniere_maintenance(LocalDate derniere_maintenance) { this.derniere_maintenance = derniere_maintenance; }

    public LigneProduction getLigne() { return ligne; }
    public void setLigne(LigneProduction ligne) { this.ligne = ligne; }
}

