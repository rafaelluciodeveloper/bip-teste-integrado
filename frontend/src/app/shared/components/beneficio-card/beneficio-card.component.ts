import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Beneficio } from '../../../models/beneficio.model';

/**
 * Componente de card para exibir benefício.
 * Dumb Component - apenas apresenta dados e emite eventos.
 * 
 * @author Rafael Lucio
 * @version 1.0
 * @since 1.0
 */
@Component({
  selector: 'app-beneficio-card',
  templateUrl: './beneficio-card.component.html',
  styleUrls: ['./beneficio-card.component.scss'],
  imports: [CommonModule],
  standalone: true
})
export class BeneficioCardComponent {
  @Input() beneficio!: Beneficio;
  @Input() showActions = true;
  @Output() edit = new EventEmitter<Beneficio>();
  @Output() delete = new EventEmitter<Beneficio>();
  @Output() transfer = new EventEmitter<Beneficio>();

  /**
   * Emite evento de edição do benefício.
   */
  onEdit(): void {
    this.edit.emit(this.beneficio);
  }

  /**
   * Emite evento de exclusão do benefício.
   */
  onDelete(): void {
    this.delete.emit(this.beneficio);
  }

  /**
   * Emite evento de transferência do benefício.
   */
  onTransfer(): void {
    this.transfer.emit(this.beneficio);
  }
}
