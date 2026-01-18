import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-dashboard-rendement',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.css']
})
export class DashboardRendementPage {
  private http = inject(HttpClient);

  rendements: any[] = [];
  syntheseEmployes: any[] = [];
  syntheseMachines: any[] = [];

  ngOnInit() {
    this.loadData();
  }

  loadData() {
    // 🔹 Rendements par production
    this.http.get<any[]>('http://localhost:8080/api/rendements')
      .subscribe(data => this.rendements = data);

    // 🔹 Synthèse par employé
    this.http.get<any[]>('http://localhost:8080/api/rendements/synthese/employes')
      .subscribe(data => this.syntheseEmployes = data);

    // 🔹 Synthèse par machine
    this.http.get<any[]>('http://localhost:8080/api/rendements/synthese/machines')
      .subscribe(data => this.syntheseMachines = data);
  }
}

