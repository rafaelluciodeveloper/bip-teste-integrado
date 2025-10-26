import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

/**
 * Componente de loading genérico.
 * Dumb Component - apenas apresenta estado de carregamento.
 * 
 * @author Rafael Lucio
 * @version 1.0
 * @since 1.0
 */
@Component({
  selector: 'app-loading',
  templateUrl: './loading.component.html',
  styleUrls: ['./loading.component.scss'],
  imports: [CommonModule],
  standalone: true
})
export class LoadingComponent {
  @Input() message = 'Carregando...';
  @Input() size: 'sm' | 'md' | 'lg' = 'md';
}
