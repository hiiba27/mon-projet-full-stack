import { provideRouter, Routes , withRouterConfig } from '@angular/router';

// 🔹 Importation des composants standalone
import { DashboardEmployesPage } from './pages/employees/dashboard/dashboard';
import { DashboardMachinePage } from './pages/machines/dashboard/dashboard';
import { DashboardCommandePage } from './pages/commandes/dashboard/dashboard';
import { DashboardProductionPage } from './pages/productions/dashboard/dashboard';
import { DashboardProduitPage } from './pages/produits/dashboard/dashboard';
import { DashboardRendementPage } from './pages/rendements/dashboard/dashboard';
import { DashboardRapportPage } from './pages/rapports/dashboard/dashboard';

// 🔹 Définition des routes
const routes: Routes = [
  { path: '', redirectTo: 'employees', pathMatch: 'full' },

  { path: 'employees', component: DashboardEmployesPage },
  { path: 'machines', component: DashboardMachinePage },
  { path: 'commandes', component: DashboardCommandePage },
  { path: 'productions', component: DashboardProductionPage },
  { path: 'produits', component: DashboardProduitPage },
  { path: 'rendements', component: DashboardRendementPage },
  { path: 'rapports', component: DashboardRapportPage },

  // 🔹 Route fallback si aucune correspondance
  { path: '**', redirectTo: 'employees' }
];

// 🔹 Fourniture du router à l'application


export const appConfig = [
  provideRouter(routes, withRouterConfig({ onSameUrlNavigation: 'reload' }))
];

