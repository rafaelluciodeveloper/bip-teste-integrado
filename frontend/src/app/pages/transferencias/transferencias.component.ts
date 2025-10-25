import { Component, OnInit, OnDestroy } from '@angular/core';
import { BeneficioService } from '../../core/services/beneficio.service';
import { Beneficio, TransferenciaRequest } from '../../models/beneficio.model';
import { Subscription } from 'rxjs';

/**
 * Componente de gerenciamento de transferências.
 * Smart Component - orquestra componentes e serviços.
 * 
 * @author Rafael Lucio
 * @version 1.0
 * @since 1.0
 */
@Component({
  selector: 'app-transferencias',
  templateUrl: './transferencias.component.html',
  styleUrls: ['./transferencias.component.scss']
})
export class TransferenciasComponent implements OnInit, OnDestroy {
  beneficios: Beneficio[] = [];
  loading = false;
  error: string | null = null;
  success: string | null = null;
  private subscriptions = new Subscription();

  // Formulário de transferência
  transferencia: TransferenciaRequest = {
    fromId: 0,
    toId: 0,
    amount: 0
  };

  constructor(private beneficioService: BeneficioService) { }

  /**
   * Inicializa o componente carregando os benefícios.
   */
  ngOnInit(): void {
    this.carregarBeneficios();
  }

  /**
   * Limpa as subscrições ao destruir o componente.
   */
  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
  }

  /**
   * Carrega todos os benefícios do sistema.
   */
  carregarBeneficios(): void {
    this.loading = true;
    this.error = null;
    
    const sub = this.beneficioService.getAll().subscribe({
      next: (beneficios) => {
        this.beneficios = beneficios;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erro ao carregar benefícios: ' + err.message;
        this.loading = false;
      }
    });
    
    this.subscriptions.add(sub);
  }

  /**
   * Realiza a transferência entre benefícios.
   */
  realizarTransferencia(): void {
    if (this.transferencia.fromId === this.transferencia.toId) {
      this.error = 'Benefício origem e destino não podem ser iguais';
      return;
    }

    if (this.transferencia.amount <= 0) {
      this.error = 'Valor deve ser positivo';
      return;
    }

    this.loading = true;
    this.error = null;
    this.success = null;

    const sub = this.beneficioService.transfer(this.transferencia).subscribe({
      next: () => {
        this.success = 'Transferência realizada com sucesso!';
        this.resetarFormulario();
        this.carregarBeneficios();
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erro na transferência: ' + err.message;
        this.loading = false;
      }
    });
    
    this.subscriptions.add(sub);
  }

  /**
   * Reseta o formulário de transferência.
   */
  resetarFormulario(): void {
    this.transferencia = {
      fromId: 0,
      toId: 0,
      amount: 0
    };
  }

  /**
   * Retorna benefícios disponíveis para origem.
   * 
   * @returns lista de benefícios ativos
   */
  get beneficiosOrigem(): Beneficio[] {
    return this.beneficios.filter(b => b.ativo);
  }

  /**
   * Retorna benefícios disponíveis para destino.
   * 
   * @returns lista de benefícios ativos excluindo o origem
   */
  get beneficiosDestino(): Beneficio[] {
    return this.beneficios.filter(b => b.ativo && b.id !== this.transferencia.fromId);
  }

  /**
   * Retorna o benefício origem selecionado.
   * 
   * @returns benefício origem ou null
   */
  get beneficioOrigem(): Beneficio | null {
    return this.beneficios.find(b => b.id === this.transferencia.fromId) || null;
  }

  /**
   * Retorna o benefício destino selecionado.
   * 
   * @returns benefício destino ou null
   */
  get beneficioDestino(): Beneficio | null {
    return this.beneficios.find(b => b.id === this.transferencia.toId) || null;
  }
}
