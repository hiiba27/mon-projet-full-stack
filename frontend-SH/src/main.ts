// 🔹 Import Chart.js et enregistrement des composants nécessaires
import { Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale } from 'chart.js';

// ✅ Enregistrer les composants nécessaires pour les bar charts
ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale);

// 🔹 Import Angular
import { bootstrapApplication } from '@angular/platform-browser';
import { provideHttpClient } from '@angular/common/http';

// 🔹 Import ton App et config
import { App } from './app/app';
import { appConfig } from './app/app.config';

// ✅ Bootstrap de l'application
bootstrapApplication(App, {
  providers: [
    ...appConfig,       // routing
    provideHttpClient() // client HTTP
  ]
}).catch(err => console.error(err));

