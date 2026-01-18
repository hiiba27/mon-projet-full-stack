import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Produit {
  id?: number;
  nom: string;
  type: string;
  temps_standard: number;
}

@Injectable({ providedIn: 'root' })
export class ProduitService {
  private apiUrl = 'http://localhost:8080/api/produits';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Produit[]> {
    return this.http.get<Produit[]>(this.apiUrl);
  }

  create(p: Produit): Observable<Produit> {
    return this.http.post<Produit>(this.apiUrl, p);
  }

  update(id: number, p: Produit): Observable<Produit> {
    return this.http.put<Produit>(`${this.apiUrl}/${id}`, p);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
