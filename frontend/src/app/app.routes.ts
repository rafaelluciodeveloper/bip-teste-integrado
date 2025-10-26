import { Routes } from '@angular/router';

import { HomeComponent } from './pages/home/home.component';
import { BeneficiosComponent } from './pages/beneficios/beneficios.component';
import { TransferenciasComponent } from './pages/transferencias/transferencias.component';
import { HistoricoComponent } from './pages/historico/historico.component';

/**
 * Configuração de rotas da aplicação.
 * Define as rotas principais seguindo a estrutura standalone.
 * 
 * @author Rafael Lucio
 * @version 1.0
 * @since 1.0
 */
export const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'beneficios', component: BeneficiosComponent },
  { path: 'transferencias', component: TransferenciasComponent },
  { path: 'historico', component: HistoricoComponent },
  { path: '**', redirectTo: '/home' }
];
