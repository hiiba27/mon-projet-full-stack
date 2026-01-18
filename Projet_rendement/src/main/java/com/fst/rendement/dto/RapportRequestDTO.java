package com.fst.rendement.dto;

public class RapportRequestDTO {
    private String typeRapport; // global, employe, machine, commande
    private String dateDebut;
    private String dateFin;
    private Integer employeId;
    private Integer machineId;

    public String getTypeRapport() { return typeRapport; }
    public void setTypeRapport(String typeRapport) { this.typeRapport = typeRapport; }

    public String getDateDebut() { return dateDebut; }
    public void setDateDebut(String dateDebut) { this.dateDebut = dateDebut; }

    public String getDateFin() { return dateFin; }
    public void setDateFin(String dateFin) { this.dateFin = dateFin; }

    public Integer getEmployeId() { return employeId; }
    public void setEmployeId(Integer employeId) { this.employeId = employeId; }

    public Integer getMachineId() { return machineId; }
    public void setMachineId(Integer machineId) { this.machineId = machineId; }
}
