package com.fst.rendement.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "rendements")
public class Rendement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String periode;          // ex: "Janvier 2026"
    private float taux_rendement;    // pièces/heure
    private float taux_qualite;      // %
    private float rendement_global;  // combinaison rendement * qualité

    // 🔹 Relation avec Production
    @OneToOne
    @JoinColumn(name = "production_id")
    private Production production;

    // 🔹 Relation avec Rapport (optionnel)
    @OneToOne(mappedBy = "rendement")
    private Rapport rapport;

    // ====== GETTERS & SETTERS ======
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getPeriode() { return periode; }
    public void setPeriode(String periode) { this.periode = periode; }

    public float getTaux_rendement() { return taux_rendement; }
    public void setTaux_rendement(float taux_rendement) { this.taux_rendement = taux_rendement; }

    public float getTaux_qualite() { return taux_qualite; }
    public void setTaux_qualite(float taux_qualite) { this.taux_qualite = taux_qualite; }

    public float getRendement_global() { return rendement_global; }
    public void setRendement_global(float rendement_global) { this.rendement_global = rendement_global; }

    public Production getProduction() { return production; }
    public void setProduction(Production production) { this.production = production; }

    public Rapport getRapport() { return rapport; }
    public void setRapport(Rapport rapport) { this.rapport = rapport; }
}
