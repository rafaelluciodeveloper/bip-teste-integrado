import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

// Importações dos componentes das páginas
import { HomeComponent } from './pages/home/home.component';
import { BeneficiosComponent } from './pages/beneficios/beneficios.component';
import { TransferenciasComponent } from './pages/transferencias/transferencias.component';
import { HistoricoComponent } from './pages/historico/historico.component';

/**
 * Configuração de rotas da aplicação.
 * Define as rotas principais seguindo a estrutura modular.
 * 
 * @author Rafael Lucio
 * @version 1.0
 * @since 1.0
 */
const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'beneficios', component: BeneficiosComponent },
  { path: 'transferencias', component: TransferenciasComponent },
  { path: 'historico', component: HistoricoComponent },
  { path: '**', redirectTo: '/home' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
