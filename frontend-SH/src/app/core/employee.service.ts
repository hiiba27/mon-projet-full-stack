import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Employee { 
  id?: number; 
  username: string; 
  poste: string; 
  rendement_moyen: number;   // objectif en unités/heure
  heures_travaillees: number; 
  production_totale: number; 
  rendementReel?: number;    // rendement réel calculé
} 

export interface EmployeeStats { 
  total: number; 
  rendement_moyen: number; 
  heures_travaillees: number; 
  production_totale: number; 
}

@Injectable({ providedIn: 'root' })
export class EmployeeService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api/employees';

  getAll(): Observable<Employee[]> {
    return this.http.get<Employee[]>(this.baseUrl);
  }

  create(emp: Employee): Observable<Employee> {
    return this.http.post<Employee>(this.baseUrl, emp);
  }

  update(id: number, emp: Employee): Observable<Employee> {
    return this.http.put<Employee>(`${this.baseUrl}/${id}`, emp);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  getStats(): Observable<EmployeeStats> {
    return this.http.get<EmployeeStats>(`${this.baseUrl}/stats`);
  }

 
}


