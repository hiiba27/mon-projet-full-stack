import { Component } from '@angular/core';
import { RouterOutlet, RouterModule } from '@angular/router';
import { NgClass } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    RouterModule,
    NgClass,       // ✅ pour [ngClass]
    FormsModule    // ✅ pour [(ngModel)]
  ],
  templateUrl: './app.html',
  styleUrls: ['./app.css']
})
export class App {
  currentTheme: string = 'theme-baby'; // thème par défaut
}

