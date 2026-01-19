import { provideRouter, Routes } from '@angular/router';

// Layout
import { DashboardLayoutComponent } from './pages/layout/dashboard/dashboard';

// Login
import { DashboardLoginPage } from './pages/login/dashboard/dashboard';

// Pages métiers
import { DashboardEmployesPage } from './pages/employees/dashboard/dashboard';
import { DashboardMachinePage } from './pages/machines/dashboard/dashboard';
import { DashboardCommandePage } from './pages/commandes/dashboard/dashboard';
import { DashboardProductionPage } from './pages/productions/dashboard/dashboard';
import { DashboardProduitPage } from './pages/produits/dashboard/dashboard';
import { DashboardRendementPage } from './pages/rendements/dashboard/dashboard';
import { DashboardRapportPage } from './pages/rapports/dashboard/dashboard';

const routes: Routes = [

  // LOGIN
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: DashboardLoginPage },

  // LAYOUT
  {
    path: 'app',
    component: DashboardLayoutComponent,
    children: [
      { path: 'employees', component: DashboardEmployesPage },
      { path: 'machines', component: DashboardMachinePage },
      { path: 'commandes', component: DashboardCommandePage },
      { path: 'produits', component: DashboardProduitPage },
      { path: 'productions', component: DashboardProductionPage },
      { path: 'rapports', component: DashboardRapportPage },
      { path: 'rendements', component: DashboardRendementPage },
      { path: '', redirectTo: 'employees', pathMatch: 'full' }
    ]
  },

  { path: '**', redirectTo: 'login' }
];

export const appConfig = [
  provideRouter(routes)
];
