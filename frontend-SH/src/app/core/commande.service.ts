import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Produit } from './produit.service';

export interface Commande {
  id?: number;
  client: string;
  date_commande: string;
  quantite_commandee: number;
  produit?: Produit | null; // ✅ autorise null
}

@Injectable({ providedIn: 'root' })
export class CommandeService {
  private apiUrl = 'http://localhost:8080/api/commandes';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Commande[]> {
    return this.http.get<Commande[]>(this.apiUrl, { withCredentials: true });
  }

  create(c: Commande): Observable<Commande> {
    return this.http.post<Commande>(this.apiUrl, c, { withCredentials: true });
  }

  update(id: number, c: Commande): Observable<Commande> {
    return this.http.put<Commande>(`${this.apiUrl}/${id}`, c, { withCredentials: true });
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, { withCredentials: true });
  }
}
