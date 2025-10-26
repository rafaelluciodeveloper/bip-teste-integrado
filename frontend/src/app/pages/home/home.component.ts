import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BeneficioService } from '../../core/services/beneficio.service';
import { Beneficio } from '../../models/beneficio.model';
import { BeneficioListComponent } from '../../shared/components/beneficio-list/beneficio-list.component';
import { LoadingComponent } from '../../shared/components/loading/loading.component';
import { ErrorComponent } from '../../shared/components/error/error.component';

/**
 * Componente da página inicial.
 * Smart Component - orquestra componentes e serviços.
 * 
 * @author Rafael Lucio
 * @version 1.0
 * @since 1.0
 */
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  imports: [CommonModule, BeneficioListComponent, LoadingComponent, ErrorComponent],
  standalone: true
})
export class HomeComponent implements OnInit {
  beneficios: Beneficio[] = [];
  loading = false;
  error: string | null = null;

  private readonly beneficioService = inject(BeneficioService);

  /**
   * Inicializa o componente carregando os benefícios ativos.
   */
  ngOnInit(): void {
    this.carregarBeneficiosAtivos();
  }

  /**
   * Carrega apenas os benefícios ativos para exibição na home.
   */
  carregarBeneficiosAtivos(): void {
    this.loading = true;
    this.error = null;
    
    this.beneficioService.getAtivos().subscribe({
      next: (beneficios) => {
        this.beneficios = beneficios;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erro ao carregar benefícios: ' + err.message;
        this.loading = false;
      }
    });
  }
}
