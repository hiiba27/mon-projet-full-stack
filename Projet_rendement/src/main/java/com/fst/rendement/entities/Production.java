package com.fst.rendement.entities;

import java.util.Date;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Production {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd")   // ✅ accepte date au format yyyy-MM-dd
    @Temporal(TemporalType.DATE)
    private Date date;

    private int quantite_produite;
    private float temps_travail;
    private int defaults;

    // 🔹 RELATIONS
    @ManyToOne
    private Employee employe;

    @ManyToOne
    private Machine machine;

    @ManyToOne
    private Commande commande;

    // 🔹 GETTERS & SETTERS
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public int getQuantite_produite() { return quantite_produite; }
    public void setQuantite_produite(int quantite_produite) { this.quantite_produite = quantite_produite; }

    public float getTemps_travail() { return temps_travail; }
    public void setTemps_travail(float temps_travail) { this.temps_travail = temps_travail; }

    public int getDefaults() { return defaults; }
    public void setDefaults(int defaults) { this.defaults = defaults; }

    public Employee getEmploye() { return employe; }
    public void setEmploye(Employee employe) { this.employe = employe; }

    public Machine getMachine() { return machine; }
    public void setMachine(Machine machine) { this.machine = machine; }

    public Commande getCommande() { return commande; }
    public void setCommande(Commande commande) { this.commande = commande; }
}



