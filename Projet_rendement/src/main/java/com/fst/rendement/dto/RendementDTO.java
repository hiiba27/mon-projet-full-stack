package com.fst.rendement.dto;

public class RendementDTO {
    private Integer productionId;
    private String date;
    private float tauxRendement;
    private float tauxQualite;
    private float rendementGlobal;
    private String employe;
    private String machine;

    public RendementDTO(Integer productionId, String date, float tauxRendement, float tauxQualite,
                        float rendementGlobal, String employe, String machine) {
        this.productionId = productionId;
        this.date = date;
        this.tauxRendement = tauxRendement;
        this.tauxQualite = tauxQualite;
        this.rendementGlobal = rendementGlobal;
        this.employe = employe;
        this.machine = machine;
    }

    public Integer getProductionId() { return productionId; }
    public String getDate() { return date; }
    public float getTauxRendement() { return tauxRendement; }
    public float getTauxQualite() { return tauxQualite; }
    public float getRendementGlobal() { return rendementGlobal; }
    public String getEmploye() { return employe; }
    public String getMachine() { return machine; }
}


