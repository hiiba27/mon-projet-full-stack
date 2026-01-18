import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { ProductionService } from '../../../core/production.service';
import { EmployeeService } from '../../../core/employee.service';
import { MachineService } from '../../../core/machine.service';
import { CommandeService } from '../../../core/commande.service';

@Component({
  selector: 'app-dashboard-production',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.css']
})
export class DashboardProductionPage {
  productions: any[] = [];
  filtered: any[] = [];
  employees: any[] = [];
  machines: any[] = [];
  commandes: any[] = [];

  search: string = '';
  selected: any | null = null;
  form: any = {
    date: '',
    quantite_produite: 0,
    temps_travail: 0,
    defaults: 0,
    employe: null,
    machine: null,
    commande: null
  };

  // ✅ Services injectés
  private productionService = inject(ProductionService);
  private employeeService = inject(EmployeeService);
  private machineService = inject(MachineService);
  private commandeService = inject(CommandeService);

  ngOnInit() {
    this.loadProductions();
    this.loadEmployees();
    this.loadMachines();
    this.loadCommandes();
  }

  // 🔹 Charger les données
  loadProductions() {
    this.productionService.getAll().subscribe((data: any[]) => {
      this.productions = data;
      this.filtered = data;
    });
  }

  loadEmployees() {
    this.employeeService.getAll().subscribe((data: any[]) => {
      this.employees = data;
    });
  }

  loadMachines() {
    this.machineService.getAll().subscribe((data: any[]) => {
      this.machines = data;
    });
  }

  loadCommandes() {
    this.commandeService.getAll().subscribe((data: any[]) => {
      this.commandes = data;
    });
  }

  // 🔹 Filtrer
  filter() {
    this.filtered = this.productions.filter(p =>
      p.date.toString().includes(this.search)
    );
  }

  showList() {
    this.filtered = [...this.productions];
  }

  // 🔹 Formulaire
  resetForm() {
    this.form = {
      date: '',
      quantite_produite: 0,
      temps_travail: 0,
      defaults: 0,
      employe: null,
      machine: null,
      commande: null
    };
    this.selected = null;
  }

  edit(p: any) {
    this.selected = p;
    this.form = { ...p };
  }

  delete(id: number) {
    this.productionService.delete(id).subscribe(() => this.loadProductions());
  }

  save() {
    const payload: any = {
      date: this.form.date,
      quantite_produite: this.form.quantite_produite,
      temps_travail: this.form.temps_travail,
      defaults: this.form.defaults,
      employe: this.form.employe ? { id: this.form.employe.id } : null,
      machine: this.form.machine ? { id: this.form.machine.id } : null,
      commande: this.form.commande ? { id: this.form.commande.id } : null
    };

    if (this.selected) {
      this.productionService.update(this.selected.id!, payload).subscribe(() => {
        this.loadProductions();
        this.resetForm();
      });
    } else {
      this.productionService.create(payload).subscribe(() => {
        this.loadProductions();
        this.resetForm();
      });
    }
  }
}





