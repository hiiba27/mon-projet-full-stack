package com.fst.rendement.dto;

public class RendementSyntheseDTO {
    private String nom;              // nom employé ou machine
    private String type;             // "employe" ou "machine"
    private float moyenneRendement;
    private float moyenneQualite;
    private float moyenneGlobale;

    public RendementSyntheseDTO(String nom, String type, float moyenneRendement,
                                float moyenneQualite, float moyenneGlobale) {
        this.nom = nom;
        this.type = type;
        this.moyenneRendement = moyenneRendement;
        this.moyenneQualite = moyenneQualite;
        this.moyenneGlobale = moyenneGlobale;
    }

    public String getNom() { return nom; }
    public String getType() { return type; }
    public float getMoyenneRendement() { return moyenneRendement; }
    public float getMoyenneQualite() { return moyenneQualite; }
    public float getMoyenneGlobale() { return moyenneGlobale; }
}
