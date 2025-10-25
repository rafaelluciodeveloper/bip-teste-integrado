import { Component } from '@angular/core';

/**
 * Componente de cabeçalho da aplicação.
 * Dumb Component - apenas apresenta informações.
 * 
 * @author Rafael Lucio
 * @version 1.0
 * @since 1.0
 */
@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  title = 'Sistema de Benefícios';
}
