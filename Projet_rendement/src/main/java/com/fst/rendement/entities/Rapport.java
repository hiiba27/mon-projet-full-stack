package com.fst.rendement.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "rapports")
public class Rapport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String titre;
    private String periode;
    private Date date_generation;
    private String fichier_url;

    @OneToOne
    @JoinColumn(name = "rendement_id")
    private Rendement rendement;

    // Getters & Setters
    public Integer getId(){
        return id;
    }
    public void setId(Integer id){
        this.id = id;
    }

    public String getTitre(){
        return titre;
    }
    public void setTitre(String titre){
        this.titre = titre;
    }

    public String getPeriode(){
        return periode;
    }
    public void setPeriode(String periode){
        this.periode = periode;
    }

    public Date getDate_generation(){
        return date_generation;
    }
    public void setDate_generation(Date date_generation){
        this.date_generation = date_generation;
    }

    public String getFichier_url(){
        return fichier_url;
    }
    public void setFichier_url(String fichier_url){
        this.fichier_url = fichier_url;
    }

    public Rendement getRendement(){
        return rendement;
    }
    public void setRendement(Rendement rendement){
        this.rendement = rendement;
    }
}

