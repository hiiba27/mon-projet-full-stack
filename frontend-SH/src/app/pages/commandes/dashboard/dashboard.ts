import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CommandeService, Commande } from '../../../core/commande.service';
import { ProduitService, Produit } from '../../../core/produit.service';

@Component({
  selector: 'app-dashboard-commande',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.css']
})
export class DashboardCommandePage {
  private commandeService = inject(CommandeService);
  private produitService = inject(ProduitService);

  commandes: Commande[] = [];
  produits: Produit[] = [];
  filtered: Commande[] = [];

  form: Commande = {
    client: '',
    date_commande: '',
    quantite_commandee: 0,
    produit: { id: 0, nom: '', type: '', temps_standard: 0 }
  };

  selected: Commande | null = null;
  search: string = '';

  ngOnInit() {
    this.loadCommandes();
    this.loadProduits();
  }

  loadCommandes() {
    this.commandeService.getAll().subscribe(data => {
      this.commandes = data;
      this.filtered = data;
    });
  }

  loadProduits() {
    this.produitService.getAll().subscribe(data => {
      this.produits = data;
    });
  }

  filter() {
    this.filtered = this.commandes.filter(c =>
      c.client.toLowerCase().includes(this.search.toLowerCase())
    );
  }

  // ✅ Ajout de la méthode showList()
  showList() {
    this.filtered = [...this.commandes];
  }

  edit(c: Commande) {
    this.selected = c;
    this.form = { ...c };
  }

  save() {
    if (this.selected) {
      this.commandeService.update(this.selected.id!, this.form).subscribe(() => {
        this.loadCommandes();
        this.selected = null;
        this.resetForm();
      });
    } else {
      this.commandeService.create(this.form).subscribe(() => {
        this.loadCommandes();
        this.resetForm();
      });
    }
  }

  delete(id: number) {
    this.commandeService.delete(id).subscribe(() => {
      this.loadCommandes();
    });
  }

  resetForm() {
    this.form = {
      client: '',
      date_commande: '',
      quantite_commandee: 0,
      produit: { id: 0, nom: '', type: '', temps_standard: 0 }
    };
    this.selected = null;
  }
}
