import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { BeneficioCardComponent } from './components/beneficio-card/beneficio-card.component';
import { BeneficioListComponent } from './components/beneficio-list/beneficio-list.component';
import { LoadingComponent } from './components/loading/loading.component';
import { ErrorComponent } from './components/error/error.component';

/**
 * Módulo Shared - Componentes reutilizáveis (Dumb Components).
 * Mini-biblioteca de componentes que podem ser usados em qualquer lugar.
 * 
 * @author Rafael Lucio
 * @version 1.0
 * @since 1.0
 */
@NgModule({
  declarations: [
    HeaderComponent,
    FooterComponent,
    BeneficioCardComponent,
    BeneficioListComponent,
    LoadingComponent,
    ErrorComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule
  ],
  exports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    HeaderComponent,
    FooterComponent,
    BeneficioCardComponent,
    BeneficioListComponent,
    LoadingComponent,
    ErrorComponent
  ]
})
export class SharedModule { }
