import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../../core/auth.service';
 // 👈 importe ton AuthService

@Component({
  selector: 'app-dashboard-rendement',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.css']
})
export class DashboardRendementPage {
  private http = inject(HttpClient);
  private auth = inject(AuthService); // 👈 injection du service d’authentification

  rendements: any[] = [];
  syntheseEmployes: any[] = [];
  syntheseMachines: any[] = [];

  ngOnInit() {
    this.loadData();
  }

  loadData() {
    const headers = this.auth.getAuthHeaders(); // 👈 récupère les headers avec Basic Auth

    // 🔹 Rendements par production
    this.http.get<any[]>('http://localhost:8080/api/rendements', { headers })
      .subscribe({
        next: data => this.rendements = data,
        error: err => console.error('Erreur rendements:', err)
      });

    // 🔹 Synthèse par employé
    this.http.get<any[]>('http://localhost:8080/api/rendements/synthese/employes', { headers })
      .subscribe({
        next: data => this.syntheseEmployes = data,
        error: err => console.error('Erreur synthèse employés:', err)
      });

    // 🔹 Synthèse par machine
    this.http.get<any[]>('http://localhost:8080/api/rendements/synthese/machines', { headers })
      .subscribe({
        next: data => this.syntheseMachines = data,
        error: err => console.error('Erreur synthèse machines:', err)
      });
  }
}
