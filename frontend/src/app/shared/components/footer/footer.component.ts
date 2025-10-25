import { Component } from '@angular/core';

/**
 * Componente de rodapé da aplicação.
 * Dumb Component - apenas apresenta informações.
 * 
 * @author Rafael Lucio
 * @version 1.0
 * @since 1.0
 */
@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent {
  currentYear = new Date().getFullYear();
}
