import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { Transferencia } from '../models/transferencia.model';
import { ApiService } from './api.service';

/**
 * Serviço responsável pelas operações relacionadas às transferências.
 * Gerencia o histórico e consultas de transferências.
 * 
 * @author Rafael Lucio
 * @version 1.0
 * @since 1.0
 */
@Injectable({
  providedIn: 'root'
})
export class TransferenciaService {
  private readonly apiService = inject(ApiService);

  /**
   * Lista o histórico de todas as transferências realizadas.
   * 
   * @returns Observable com lista de transferências
   */
  getHistoricoTransferencias(): Observable<Transferencia[]> {
    return this.apiService.get<Transferencia[]>('/beneficios/transferencias/historico');
  }

  /**
   * Lista o histórico de transferências de um benefício específico.
   * 
   * @param beneficioId identificador do benefício
   * @returns Observable com lista de transferências do benefício
   */
  getTransferenciasByBeneficio(beneficioId: number): Observable<Transferencia[]> {
    return this.apiService.get<Transferencia[]>(`/beneficios/${beneficioId}/transferencias`);
  }
}
