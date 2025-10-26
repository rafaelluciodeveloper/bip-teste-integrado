import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TransferenciaService } from '../../core/services/transferencia.service';
import { Transferencia } from '../../core/models/transferencia.model';

/**
 * Componente responsável por exibir o histórico de transferências.
 * Lista todas as transferências realizadas no sistema.
 * 
 * @author Rafael Lucio
 * @version 1.0
 * @since 1.0
 */
@Component({
  selector: 'app-historico',
  templateUrl: './historico.component.html',
  styleUrls: ['./historico.component.scss'],
  imports: [CommonModule],
  standalone: true
})
export class HistoricoComponent implements OnInit {

  transferencias: Transferencia[] = [];
  loading = false;
  error: string | null = null;

  private readonly transferenciaService = inject(TransferenciaService);

  ngOnInit(): void {
    this.carregarHistorico();
  }

  /**
   * Carrega o histórico de transferências.
   */
  carregarHistorico(): void {
    this.loading = true;
    this.error = null;

    this.transferenciaService.getHistoricoTransferencias().subscribe({
      next: (transferencias) => {
        this.transferencias = transferencias;
        this.loading = false;
      },
      error: (error) => {
        this.error = 'Erro ao carregar histórico de transferências';
        this.loading = false;
        console.error('Erro ao carregar histórico:', error);
      }
    });
  }

  /**
   * Formata a data para exibição.
   * 
   * @param dataString data em formato string
   * @returns data formatada
   */
  formatarData(dataString: string): string {
    const data = new Date(dataString);
    return data.toLocaleString('pt-BR');
  }

  /**
   * Formata o valor para exibição.
   * 
   * @param valor valor numérico
   * @returns valor formatado em moeda
   */
  formatarValor(valor: number): string {
    return new Intl.NumberFormat('pt-BR', {
      style: 'currency',
      currency: 'BRL'
    }).format(valor);
  }
}
