import { Component, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MachineService, Machine } from '../../../core/machine.service';
import { BaseChartDirective } from 'ng2-charts';
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  ArcElement,
  PieController,
  ChartOptions,
  ChartType
} from 'chart.js';

ChartJS.register(Title, Tooltip, Legend, ArcElement, PieController);

@Component({
  selector: 'app-dashboard-machine',
  standalone: true,
  imports: [CommonModule, FormsModule, BaseChartDirective],
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.css']
})
export class DashboardMachinePage {
  private service = inject(MachineService);
  private cdr = inject(ChangeDetectorRef);

  machines: Machine[] = [];
  filtered: Machine[] = [];
  bestMachine: Machine | null = null;

  form: Machine = {
    nom: '',
    etat: '',
    taux_utilisation: 0,
    heures_fonctionnement: 0,
    production_totale: 0,
    derniere_maintenance: ''
  };
  selected: Machine | null = null;
  search: string = '';

  pieOptions: ChartOptions = { responsive: true };
  pieType: ChartType = 'pie';
  pieData: any = {
    labels: [],
    datasets: [
      {
        data: [],
        backgroundColor: ['#2ecc71', '#3498db', '#e74c3c']
      }
    ]
  };

  ngOnInit() {
    this.loadMachines();
    this.loadBestMachine();
    this.loadEtatStats();
  }

  loadMachines() {
    this.service.getAll().subscribe(data => {
      this.machines = data;
      this.filtered = data;
    });
  }

  loadBestMachine() {
    this.service.getBestMachine().subscribe(data => {
      this.bestMachine = data;
      this.cdr.detectChanges();
    });
  }

  loadEtatStats() {
    this.service.getEtatStats().subscribe((stats: Record<string, number>) => {
      const values: number[] = Object.values(stats) as number[];
      const total: number = values.reduce((a, b) => a + b, 0);

      const labelsWithPercent = Object.entries(stats).map(([key, value]) => {
        const percent = total > 0 ? Math.round((value * 100) / total) : 0;
        return `${key} – ${percent}%`;
      });

      this.pieData = {
        labels: labelsWithPercent,
        datasets: [
          {
            data: values,
            backgroundColor: ['#2ecc71', '#3498db', '#e74c3c']
          }
        ]
      };

      this.cdr.detectChanges();
    });
  }

  filter() {
    this.filtered = this.machines.filter(m =>
      m.nom.toLowerCase().includes(this.search.toLowerCase())
    );
  }

  edit(m: Machine) {
    this.selected = m;
    this.form = { ...m };
  }

  save() {
    if (this.selected) {
      this.service.update(this.selected.id!, this.form).subscribe(() => {
        this.loadMachines();
        this.loadBestMachine();
        this.loadEtatStats();
        this.selected = null;
        this.resetForm();
      });
    } else {
      this.service.create(this.form).subscribe(() => {
        this.loadMachines();
        this.loadBestMachine();
        this.loadEtatStats();
        this.resetForm();
      });
    }
  }

  delete(id: number) {
    this.service.delete(id).subscribe(() => {
      this.loadMachines();
      this.loadBestMachine();
      this.loadEtatStats();
    });
  }

  resetForm() {
    this.form = {
      nom: '',
      etat: '',
      taux_utilisation: 0,
      heures_fonctionnement: 0,
      production_totale: 0,
      derniere_maintenance: ''
    };
    this.selected = null;
  }
}
