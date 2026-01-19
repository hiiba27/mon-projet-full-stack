import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './dashboard.html', 
  styleUrls : ['./dashboard.css'] 
})
export class DashboardLoginPage {

  username = '';
  password = '';
  errorMessage = ''; // ⚠️ doit correspondre au HTML

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  onLogin() {  // ⚠️ nom corrigé pour correspondre au HTML
    this.authService.login(this.username, this.password).subscribe({
      next: (res) => {
        // redirection selon rôle
        if(res.role === 'ROLE_ADMINISTRATEUR') {
          this.router.navigate(['/app/employees']);
        } else {
          this.router.navigate(['/app/employees']); // ou autre route selon rôle
        }
      },
      error: () => {
        this.errorMessage = 'Identifiants invalides';
      }
    });
  }
}
