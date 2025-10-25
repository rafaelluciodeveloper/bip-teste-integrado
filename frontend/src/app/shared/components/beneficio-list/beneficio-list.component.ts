import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Beneficio } from '../../../models/beneficio.model';

/**
 * Componente de lista de benefícios.
 * Dumb Component - apenas apresenta dados e emite eventos.
 * 
 * @author Rafael Lucio
 * @version 1.0
 * @since 1.0
 */
@Component({
  selector: 'app-beneficio-list',
  templateUrl: './beneficio-list.component.html',
  styleUrls: ['./beneficio-list.component.scss']
})
export class BeneficioListComponent {
  @Input() beneficios: Beneficio[] = [];
  @Input() loading = false;
  @Input() showActions = true;
  @Output() edit = new EventEmitter<Beneficio>();
  @Output() delete = new EventEmitter<Beneficio>();
  @Output() transfer = new EventEmitter<Beneficio>();

  /**
   * Emite evento de edição do benefício.
   * 
   * @param beneficio benefício a ser editado
   */
  onEdit(beneficio: Beneficio): void {
    this.edit.emit(beneficio);
  }

  /**
   * Emite evento de exclusão do benefício.
   * 
   * @param beneficio benefício a ser excluído
   */
  onDelete(beneficio: Beneficio): void {
    this.delete.emit(beneficio);
  }

  /**
   * Emite evento de transferência do benefício.
   * 
   * @param beneficio benefício para transferência
   */
  onTransfer(beneficio: Beneficio): void {
    this.transfer.emit(beneficio);
  }
}
