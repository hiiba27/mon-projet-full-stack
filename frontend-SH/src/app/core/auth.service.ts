import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth'; // URL du back Spring Boot

  constructor(private http: HttpClient) {}

  // 🔐 Login et stockage des infos utilisateur
  login(username: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/login`, { username, password }, { withCredentials: true })
      .pipe(
        tap(response => {
          localStorage.setItem('username', username);   // ⚠️ stocker le username fourni
          localStorage.setItem('password', password);   // ⚠️ stocker le password fourni
          localStorage.setItem('role', response.role);  // rôle renvoyé par le backend
        })
      );
  }

  // 🔓 Logout
  logout(): void {
    localStorage.clear();
  }

  // 🔐 Récupérer le rôle
  getRole(): string | null {
    return localStorage.getItem('role');
  }

  // ✅ Vérifier si connecté
  isLoggedIn(): boolean {
    return !!localStorage.getItem('username');
  }

  // 🔐 Générer les headers Authorization pour les autres services
  getAuthHeaders(): HttpHeaders {
    const username = localStorage.getItem('username');
    const password = localStorage.getItem('password');

    if (!username || !password) {
      throw new Error('Utilisateur non authentifié');
    }

    return new HttpHeaders({
      'Authorization': 'Basic ' + btoa(username + ':' + password),
      'Content-Type': 'application/json'
    });
  }
}
