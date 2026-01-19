import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Employee { 
  id?: number; 
  username: string; 
  poste: string; 
  taux_assiduite: number;   // objectif en unités/heure
  heures_travaillees: number; 
  production_totale: number; 
} 

export interface EmployeeStats { 
  total: number; 
  taux_assiduite: number; 
  heures_travaillees: number; 
  production_totale: number; 
}

@Injectable({ providedIn: 'root' })
export class EmployeeService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api/employees';

  // 👉 Temporary hardcoded credentials (replace with login form later)
  private username = 'admin';
  private password = 'admin123';

  // Helper to build Authorization header
  private getAuthHeaders(): HttpHeaders {
    return new HttpHeaders({
      'Authorization': 'Basic ' + btoa(this.username + ':' + this.password),
      'Content-Type': 'application/json'
    });
  }

  // GET all employees
  getAll(): Observable<Employee[]> {
    return this.http.get<Employee[]>(this.baseUrl, { headers: this.getAuthHeaders() });
  }

  // POST create new employee
  create(emp: Employee): Observable<Employee> {
    return this.http.post<Employee>(this.baseUrl, emp, { headers: this.getAuthHeaders() });
  }

  // PUT update employee by ID
  update(id: number, emp: Employee): Observable<Employee> {
    return this.http.put<Employee>(`${this.baseUrl}/${id}`, emp, { headers: this.getAuthHeaders() });
  }

  // DELETE employee by ID
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`, { headers: this.getAuthHeaders() });
  }

  // GET employee stats
  getStats(): Observable<EmployeeStats> {
    return this.http.get<EmployeeStats>(`${this.baseUrl}/stats`, { headers: this.getAuthHeaders() });
  }
}
