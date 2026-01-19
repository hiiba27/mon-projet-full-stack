import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../../core/auth.service'; // 👈 chemin corrigé vers ton AuthService

@Component({
  selector: 'app-rapport-generation',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.css']
})
export class DashboardRapportPage implements OnInit {
  private http = inject(HttpClient);
  private auth = inject(AuthService); // 👈 injection du service d’authentification

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
    const headers = this.auth.getAuthHeaders();

    // Charger la liste des employés
    this.http.get<any[]>('http://localhost:8080/api/employees', { headers })
      .subscribe({
        next: data => this.employes = data,
        error: err => console.error('Erreur chargement employés:', err)
      });

    // Charger la liste des machines
    this.http.get<any[]>('http://localhost:8080/api/machines', { headers })
      .subscribe({
        next: data => this.machines = data,
        error: err => console.error('Erreur chargement machines:', err)
      });
  }

  // Générer rapport
  genererRapport() {
    const headers = this.auth.getAuthHeaders();
    const params = {
      typeRapport: this.typeRapport || 'global',
      dateDebut: this.dateDebut,
      dateFin: this.dateFin,
      employeId: this.employeId || null,
      machineId: this.machineId || null
    };

    this.http.post<any[]>('http://localhost:8080/api/rapports/generer', params, { headers })
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
    const headers = this.auth.getAuthHeaders();
    const params = {
      typeRapport: this.typeRapport,
      dateDebut: this.dateDebut,
      dateFin: this.dateFin,
      employeId: this.employeId || null,
      machineId: this.machineId || null
    };

    this.http.post('http://localhost:8080/api/rapports/export/pdf', params, { headers, responseType: 'blob' })
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
    const headers = this.auth.getAuthHeaders();
    const emails = this.destinataires.split(',').map(e => e.trim()).filter(e => e.length > 0);

    const params = {
      typeRapport: this.typeRapport || 'global',
      dateDebut: this.dateDebut || null,
      dateFin: this.dateFin || null,
      employeId: this.employeId || null,
      machineId: this.machineId || null,
      destinataires: emails
    };

    this.http.post('http://localhost:8080/api/rapports/envoyer', params, { headers, responseType: 'text' })
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
