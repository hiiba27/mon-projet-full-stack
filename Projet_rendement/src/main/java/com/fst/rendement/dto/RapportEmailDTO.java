package com.fst.rendement.dto;

import java.util.List;

public class RapportEmailDTO {
    private String typeRapport;
    private String dateDebut;
    private String dateFin;
    private Integer employeId;
    private Integer machineId;
    private List<String> destinataires;

    // Getters & Setters
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

    public List<String> getDestinataires() { return destinataires; }
    public void setDestinataires(List<String> destinataires) { this.destinataires = destinataires; }
}
