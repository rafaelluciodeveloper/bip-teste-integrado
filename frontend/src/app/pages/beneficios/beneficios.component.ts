import { Component, OnInit, OnDestroy, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BeneficioService } from '../../core/services/beneficio.service';
import { Beneficio, TransferenciaRequest } from '../../models/beneficio.model';
import { Subscription } from 'rxjs';
import { BeneficioListComponent } from '../../shared/components/beneficio-list/beneficio-list.component';

/**
 * Componente de gerenciamento de benefícios.
 * Smart Component - orquestra componentes e serviços.
 * 
 * @author Rafael Lucio
 * @version 1.0
 * @since 1.0
 */
@Component({
  selector: 'app-beneficios',
  templateUrl: './beneficios.component.html',
  styleUrls: ['./beneficios.component.scss'],
  imports: [CommonModule, FormsModule, BeneficioListComponent],
  standalone: true
})
export class BeneficiosComponent implements OnInit, OnDestroy {
  beneficios: Beneficio[] = [];
  loading = false;
  error: string | null = null;
  private subscriptions = new Subscription();

  // Formulário de criação
  novoBeneficio: Beneficio = {
    nome: '',
    descricao: '',
    valor: 0,
    ativo: true
  };
  showForm = false;

  // Formulário de edição
  beneficioEditando: Beneficio | null = null;
  showEditForm = false;

  // Formulário de transferência
  transferencia: TransferenciaRequest = {
    fromId: 0,
    toId: 0,
    amount: 0
  };
  showTransferForm = false;

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
   * Cria um novo benefício.
   */
  criarBeneficio(): void {
    if (!this.novoBeneficio.nome || this.novoBeneficio.valor <= 0) {
      this.error = 'Nome e valor são obrigatórios';
      return;
    }

    const sub = this.beneficioService.create(this.novoBeneficio).subscribe({
      next: () => {
        this.carregarBeneficios();
        this.resetarFormulario();
        this.showForm = false;
      },
      error: (err) => {
        this.error = 'Erro ao criar benefício: ' + err.message;
      }
    });
    
    this.subscriptions.add(sub);
  }

  /**
   * Inicia a edição de um benefício.
   * 
   * @param beneficio benefício a ser editado
   */
  editarBeneficio(beneficio: Beneficio): void {
    this.beneficioEditando = { ...beneficio };
    this.showEditForm = true;
  }

  /**
   * Salva as alterações do benefício editado.
   */
  salvarEdicao(): void {
    if (!this.beneficioEditando) return;

    const sub = this.beneficioService.update(this.beneficioEditando.id!, this.beneficioEditando).subscribe({
      next: () => {
        this.carregarBeneficios();
        this.cancelarEdicao();
      },
      error: (err) => {
        this.error = 'Erro ao atualizar benefício: ' + err.message;
      }
    });
    
    this.subscriptions.add(sub);
  }

  /**
   * Cancela a edição do benefício.
   */
  cancelarEdicao(): void {
    this.beneficioEditando = null;
    this.showEditForm = false;
  }

  /**
   * Exclui um benefício após confirmação.
   * 
   * @param beneficio benefício a ser excluído
   */
  excluirBeneficio(beneficio: Beneficio): void {
    if (!confirm(`Tem certeza que deseja excluir o benefício "${beneficio.nome}"?`)) {
      return;
    }

    const sub = this.beneficioService.delete(beneficio.id!).subscribe({
      next: () => {
        this.carregarBeneficios();
      },
      error: (err) => {
        this.error = 'Erro ao excluir benefício: ' + err.message;
      }
    });
    
    this.subscriptions.add(sub);
  }

  /**
   * Inicia o processo de transferência de um benefício.
   * 
   * @param beneficio benefício origem da transferência
   */
  iniciarTransferencia(beneficio: Beneficio): void {
    this.transferencia.fromId = beneficio.id!;
    this.showTransferForm = true;
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

    const sub = this.beneficioService.transfer(this.transferencia).subscribe({
      next: () => {
        this.carregarBeneficios();
        this.cancelarTransferencia();
      },
      error: (err) => {
        this.error = 'Erro na transferência: ' + err.message;
      }
    });
    
    this.subscriptions.add(sub);
  }

  /**
   * Cancela o processo de transferência.
   */
  cancelarTransferencia(): void {
    this.transferencia = { fromId: 0, toId: 0, amount: 0 };
    this.showTransferForm = false;
  }

  /**
   * Reseta o formulário de criação.
   */
  resetarFormulario(): void {
    this.novoBeneficio = {
      nome: '',
      descricao: '',
      valor: 0,
      ativo: true
    };
  }

  /**
   * Retorna benefícios disponíveis para transferência.
   * 
   * @returns lista de benefícios excluindo o origem
   */
  get beneficiosDisponiveis(): Beneficio[] {
    return this.beneficios.filter(b => b.id !== this.transferencia.fromId);
  }
}
