package com.fst.rendement.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "commandes")
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String client;
    private Date date_commande;
    private int quantite_commandee;

    @ManyToOne
    @JoinColumn(name = "produit_id") // ✅ clé étrangère
    private Produit produit;

    // Getters & Setters
    public Integer getId(){ return id; }
    public void setId(Integer id){ this.id = id; }

    public String getClient(){ return client; }
    public void setClient(String client){ this.client = client; }

    public Date getDate_commande(){ return date_commande; }
    public void setDate_commande(Date date_commande){ this.date_commande = date_commande; }

    public int getQuantite_commandee(){ return quantite_commandee; }
    public void setQuantite_commandee(int quantite_commandee){ this.quantite_commandee = quantite_commandee; }

    public Produit getProduit(){ return produit; }
    public void setProduit(Produit produit){ this.produit = produit; }
}


