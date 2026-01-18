import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Production {
  id?: number;
  date: string;
  quantite_produite: number;
  temps_travail: number;
  defaults: number;
  employe?: { id: number, username?: string } | null;
  machine?: { id: number, nom?: string } | null;
  commande?: { id: number } | null;
}

@Injectable({ providedIn: 'root' })
export class ProductionService {
  private apiUrl = 'http://localhost:8080/api/productions';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Production[]> {
    return this.http.get<Production[]>(this.apiUrl);
  }

  create(p: Production): Observable<Production> {
    return this.http.post<Production>(this.apiUrl, p);
  }

  update(id: number, p: Production): Observable<Production> {
    return this.http.put<Production>(`${this.apiUrl}/${id}`, p);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}

