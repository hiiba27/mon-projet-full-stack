import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Machine {
  id?: number;
  nom: string;
  etat: string;
  taux_utilisation: number;
  heures_fonctionnement: number;
  production_totale: number;
  derniere_maintenance: string;
  ligne?: any;
}

@Injectable({ providedIn: 'root' })
export class MachineService {
  private apiUrl = 'http://localhost:8080/api/machines';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Machine[]> {
    return this.http.get<Machine[]>(this.apiUrl);
  }

  getBestMachine(): Observable<Machine> {
    return this.http.get<Machine>(`${this.apiUrl}/best`);
  }

  getEtatStats(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/stats/etat`);
  }

  create(machine: Machine): Observable<Machine> {
    return this.http.post<Machine>(this.apiUrl, machine);
  }

  update(id: number, machine: Machine): Observable<Machine> {
    return this.http.put<Machine>(`${this.apiUrl}/${id}`, machine);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
