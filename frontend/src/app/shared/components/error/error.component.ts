import { Component, Input, Output, EventEmitter } from '@angular/core';

/**
 * Componente de erro genérico.
 * Dumb Component - apenas apresenta mensagens de erro.
 * 
 * @author Rafael Lucio
 * @version 1.0
 * @since 1.0
 */
@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.scss']
})
export class ErrorComponent {
  @Input() message = 'Ocorreu um erro inesperado';
  @Input() showRetry = true;
  @Output() retry = new EventEmitter<void>();

  /**
   * Emite evento de tentar novamente.
   */
  onRetry(): void {
    this.retry.emit();
  }
}
