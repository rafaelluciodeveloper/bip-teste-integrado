import { Component } from '@angular/core';

/**
 * Componente raiz da aplicação.
 * Define a estrutura principal com header, main e footer.
 * 
 * @author Rafael Lucio
 * @version 1.0
 * @since 1.0
 */
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'Sistema de Benefícios';
}
