package com.fst.rendement.dto;


public class EmployeeRendementDTO {
    private Integer id;
    private String username;
    private String poste;
    private float objectif;       // rendement moyen saisi
    private float rendementReel;  // rendement calculé

    public EmployeeRendementDTO(Integer id, String username, String poste, float objectif, float rendementReel) {
        this.id = id;
        this.username = username;
        this.poste = poste;
        this.objectif = objectif;
        this.rendementReel = rendementReel;
    }

    // Getters & Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPoste() { return poste; }
    public void setPoste(String poste) { this.poste = poste; }

    public float getObjectif() { return objectif; }
    public void setObjectif(float objectif) { this.objectif = objectif; }

    public float getRendementReel() { return rendementReel; }
    public void setRendementReel(float rendementReel) { this.rendementReel = rendementReel; }
}
