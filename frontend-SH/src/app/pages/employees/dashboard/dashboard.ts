import { Component, inject } from '@angular/core';
import { CommonModule, DecimalPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EmployeeService, Employee, EmployeeStats } from '../../../core/employee.service';

@Component({
  selector: 'app-dashboard-employes',
  standalone: true,
  imports: [CommonModule, FormsModule, DecimalPipe],
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.css']
})
export class DashboardEmployesPage {
  private service = inject(EmployeeService);

  // 🔹 Données
  employees: Employee[] = [];
  filtered: Employee[] = [];
  stats: EmployeeStats = { total: 0, rendement_moyen: 0, heures_travaillees: 0, production_totale: 0 };

  // 🔹 Formulaire
  form: Employee = { username: '', poste: '', rendement_moyen: 0, heures_travaillees: 0, production_totale: 0 };
  selected: Employee | null = null;

  // 🔹 Filtres
  search = '';
  posteFilter = '';

  ngOnInit() {
    this.loadEmployees();
    this.loadStats();
  }

  // Charger employés
  loadEmployees() {
    this.service.getAll().subscribe(data => {
      this.employees = data;
      this.filtered = data;
    });
  }

  // Charger stats
  loadStats() {
    this.service.getStats().subscribe(data => this.stats = data);
  }

  // Filtrer employés
  filter() {
    this.filtered = this.employees.filter(e =>
      e.username.toLowerCase().includes(this.search.toLowerCase()) &&
      (this.posteFilter === '' || e.poste === this.posteFilter)
    );
  }

  // Modifier employé
  edit(e: Employee) {
    this.selected = e;
    this.form = { ...e };
  }

  // Sauvegarder employé
  save() {
    if (this.selected) {
      this.service.update(this.selected.id!, this.form).subscribe(() => {
        this.loadEmployees();
        this.loadStats();
        this.selected = null;
        this.form = { username: '', poste: '', rendement_moyen: 0, heures_travaillees: 0, production_totale: 0 };
      });
    } else {
      this.service.create(this.form).subscribe(() => {
        this.loadEmployees();
        this.loadStats();
        this.form = { username: '', poste: '', rendement_moyen: 0, heures_travaillees: 0, production_totale: 0 };
      });
    }
  }

  // Supprimer employé
  delete(id: number) {
    this.service.delete(id).subscribe(() => {
      this.loadEmployees();
      this.loadStats();
    });
  }

  // Afficher liste (remplace + Ajouter)
  showList() {
    this.filtered = this.employees;
    this.selected = null;
    this.form = { username: '', poste: '', rendement_moyen: 0, heures_travaillees: 0, production_totale: 0 };
  }
}


