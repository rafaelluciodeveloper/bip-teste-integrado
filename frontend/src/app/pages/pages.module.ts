import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { SharedModule } from '../shared/shared.module';
import { CoreModule } from '../core/core.module';

import { HomeComponent } from './home/home.component';
import { BeneficiosComponent } from './beneficios/beneficios.component';
import { TransferenciasComponent } from './transferencias/transferencias.component';
import { HistoricoComponent } from './historico/historico.component';

/**
 * Módulo Pages - Páginas da aplicação (Smart Components).
 * Funil onde os módulos de recursos caem, mas nada é exportado.
 * 
 * @author Rafael Lucio
 * @version 1.0
 * @since 1.0
 */
@NgModule({
  declarations: [
    HomeComponent,
    BeneficiosComponent,
    TransferenciasComponent,
    HistoricoComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    SharedModule,
    CoreModule
  ]
})
export class PagesModule { }
