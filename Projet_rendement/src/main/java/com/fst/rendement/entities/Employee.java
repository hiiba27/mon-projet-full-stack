package com.fst.rendement.entities;

import jakarta.persistence.*;


@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
 // test modification pour Git

    private String username;
    private String poste;
    private Float taux_assiduite = 0f;

    private int heures_travaillees;   // 🔹 Nouveau champ
    private int production_totale;    // 🔹 Nouveau champ

    // Getters & Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPoste() { return poste; }
    public void setPoste(String poste) { this.poste = poste; }

    public float getTaux_assiduite() { return taux_assiduite; }
    public void setTaux_assiduite(float taux_assiduite) { this.taux_assiduite = taux_assiduite; }

    public int getHeures_travaillees() { return heures_travaillees; }
    public void setHeures_travaillees(int heures_travaillees) { this.heures_travaillees = heures_travaillees; }

    public int getProduction_totale() { return production_totale; }
    public void setProduction_totale(int production_totale) { this.production_totale = production_totale; }
}

