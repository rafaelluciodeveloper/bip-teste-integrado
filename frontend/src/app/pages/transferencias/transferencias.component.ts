import { Component, OnInit, OnDestroy, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BeneficioService } from '../../core/services/beneficio.service';
import { Beneficio, TransferenciaRequest } from '../../models/beneficio.model';
import { Subscription } from 'rxjs';
import { BeneficioListComponent } from '../../shared/components/beneficio-list/beneficio-list.component';

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
  styleUrls: ['./transferencias.component.scss'],
  imports: [CommonModule, FormsModule, BeneficioListComponent],
  standalone: true
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

  // Campo de texto com máscara de moeda para o valor
  amountText = '';

  private readonly beneficioService = inject(BeneficioService);

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

    const origem = this.beneficioOrigem;
    if (origem && this.transferencia.amount > origem.valor) {
      this.error = 'Valor não pode exceder o saldo do benefício de origem';
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
    this.amountText = '';
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

  /**
   * Valor máximo permitido para transferência (saldo da origem).
   */
  get maximoTransferencia(): number {
    return this.beneficioOrigem?.valor ?? 0;
  }

  /** Indica se o valor excede o saldo disponível do benefício de origem */
  get excedeSaldo(): boolean {
    const origem = this.beneficioOrigem;
    return !!origem && this.transferencia.amount > origem.valor;
  }

  /** Novo saldo da origem após a transferência */
  get novoSaldoOrigem(): number {
    const origem = this.beneficioOrigem;
    const amt = this.transferencia.amount || 0;
    return origem ? Math.max(0, Number((origem.valor - amt).toFixed(2))) : 0;
  }

  /** Novo saldo do destino após a transferência */
  get novoSaldoDestino(): number {
    const destino = this.beneficioDestino;
    const amt = this.transferencia.amount || 0;
    return destino ? Number((destino.valor + amt).toFixed(2)) : 0;
  }

  /**
   * Formata número para moeda BRL.
   */
  private formatCurrencyBRL(value: number): string {
    if (isNaN(value)) {
      return '';
    }
    return value.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
  }

  /**
   * Converte texto com máscara para número (centavos => reais).
   */
  private parseCurrencyBRL(text: string): number {
    const digits = (text || '').replace(/\D/g, '');
    const value = Number(digits) / 100;
    return isNaN(value) ? 0 : Number(value.toFixed(2));
  }

  /**
   * Handler de input do valor com máscara.
   */
  onAmountInput(event: Event): void {
    const target = event.target as HTMLInputElement;
    const numeric = this.parseCurrencyBRL(target.value);
    this.transferencia.amount = numeric;
    this.amountText = this.formatCurrencyBRL(numeric);
  }

  /**
   * Ao trocar a origem, resetar valor e máscara.
   */
  onFromChanged(value: unknown): void {
    this.transferencia.fromId = Number(value);
    this.transferencia.amount = 0;
    this.amountText = '';
  }

  onToChanged(value: unknown): void {
    this.transferencia.toId = Number(value);
  }
}
