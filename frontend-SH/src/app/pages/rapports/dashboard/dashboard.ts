import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-rapport-generation',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.css']
})
export class DashboardRapportPage implements OnInit {
  private http = inject(HttpClient);

  // Champs du formulaire
  typeRapport: string = '';
  dateDebut: string = '';
  dateFin: string = '';
  employeId?: number;
  machineId?: number;
  destinataires: string = '';

  // Résultats du rapport
  rendements: any[] = [];

  // Listes pour les menus déroulants
  employes: any[] = [];
  machines: any[] = [];

  // Messages
  message: string = '';
  messageType: 'success' | 'error' | '' = '';

  ngOnInit() {
    // Charger la liste des employés
    this.http.get<any[]>('http://localhost:8080/api/employees')
      .subscribe(data => this.employes = data);

    // Charger la liste des machines
    this.http.get<any[]>('http://localhost:8080/api/machines')
      .subscribe(data => this.machines = data);
  }

  // Générer rapport
  genererRapport() {
    const params = {
      typeRapport: this.typeRapport || 'global',
      dateDebut: this.dateDebut,
      dateFin: this.dateFin,
      employeId: this.employeId || null,
      machineId: this.machineId || null
    };

    this.http.post<any[]>('http://localhost:8080/api/rapports/generer', params)
      .subscribe({
        next: (result) => {
          this.rendements = result;
          this.message = '✅ Rapport généré avec succès';
          this.messageType = 'success';
        },
        error: () => {
          this.message = '❌ Erreur lors de la génération du rapport';
          this.messageType = 'error';
        }
      });
  }

  // Exporter PDF
  exportPdf() {
    const params = {
      typeRapport: this.typeRapport,
      dateDebut: this.dateDebut,
      dateFin: this.dateFin,
      employeId: this.employeId || null,
      machineId: this.machineId || null
    };

    this.http.post('http://localhost:8080/api/rapports/export/pdf', params, { responseType: 'blob' })
      .subscribe({
        next: (blob: Blob) => {
          const url = window.URL.createObjectURL(blob);
          const a = document.createElement('a');
          a.href = url;
          a.download = 'rapport.pdf';
          a.click();
          window.URL.revokeObjectURL(url);

          this.message = '✅ PDF exporté avec succès';
          this.messageType = 'success';
        },
        error: () => {
          this.message = '❌ Erreur lors de l’export du PDF';
          this.messageType = 'error';
        }
      });
  }

  // Envoyer rapport par email
  envoyerRapport() {
    const emails = this.destinataires.split(',').map(e => e.trim()).filter(e => e.length > 0);

    const params = {
      typeRapport: this.typeRapport || 'global',
      dateDebut: this.dateDebut || null,
      dateFin: this.dateFin || null,
      employeId: this.employeId || null,
      machineId: this.machineId || null,
      destinataires: emails
    };

    this.http.post('http://localhost:8080/api/rapports/envoyer', params, { responseType: 'text' })
      .subscribe({
        next: (res) => {
          this.message = `✅ ${res}`;
          this.messageType = 'success';
        },
        error: (err) => {
          console.error("Erreur envoi email:", err);
          this.message = err.error ? `❌ Erreur : ${err.error}` : '❌ Erreur lors de l’envoi du rapport';
          this.messageType = 'error';
        }
      });
  }
}
