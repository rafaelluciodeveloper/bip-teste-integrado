import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';

import { BeneficioService } from './services/beneficio.service';
import { TransferenciaService } from './services/transferencia.service';
import { ApiService } from './services/api.service';

/**
 * Módulo Core - Arquivos essenciais para a aplicação.
 * Contém serviços singleton, tokens de injeção, constantes, configurações.
 * 
 * @author Rafael Lucio
 * @version 1.0
 * @since 1.0
 */
@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    HttpClientModule
  ],
  providers: [
    BeneficioService,
    TransferenciaService,
    ApiService
  ]
})
export class CoreModule { }
