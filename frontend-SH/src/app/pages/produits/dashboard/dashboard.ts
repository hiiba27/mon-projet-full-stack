import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProduitService, Produit } from '../../../core/produit.service';

@Component({
  selector: 'app-dashboard-produit',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.css']
})
export class DashboardProduitPage {
  private service = inject(ProduitService);

  produits: Produit[] = [];
  filtered: Produit[] = [];
  form: Produit = { nom: '', type: '', temps_standard: 0 };
  selected: Produit | null = null;
  search: string = '';

  ngOnInit() {
    this.loadProduits();
  }

  loadProduits() {
    this.service.getAll().subscribe(data => {
      this.produits = data;
      this.filtered = data;
    });
  }

  filter() {
    this.filtered = this.produits.filter(p =>
      p.nom.toLowerCase().includes(this.search.toLowerCase())
    );
  }

  edit(p: Produit) {
    this.selected = p;
    this.form = { ...p };
  }

  save() {
    if (this.selected) {
      this.service.update(this.selected.id!, this.form).subscribe(() => {
        this.loadProduits();
        this.selected = null;
        this.resetForm();
      });
    } else {
      this.service.create(this.form).subscribe(() => {
        this.loadProduits();
        this.resetForm();
      });
    }
  }

  delete(id: number) {
    this.service.delete(id).subscribe(() => {
      this.loadProduits();
    });
  }

  resetForm() {
    this.form = { nom: '', type: '', temps_standard: 0 };
    this.selected = null;
  }
}
