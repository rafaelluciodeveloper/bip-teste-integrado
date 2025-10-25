import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Transferencia } from '../models/transferencia.model';

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

  private readonly API_URL = 'http://localhost:8080/api/v1/beneficios';

  constructor(private http: HttpClient) { }

  /**
   * Lista o histórico de todas as transferências realizadas.
   * 
   * @returns Observable com lista de transferências
   */
  getHistoricoTransferencias(): Observable<Transferencia[]> {
    return this.http.get<Transferencia[]>(`${this.API_URL}/transferencias/historico`);
  }

  /**
   * Lista o histórico de transferências de um benefício específico.
   * 
   * @param beneficioId identificador do benefício
   * @returns Observable com lista de transferências do benefício
   */
  getTransferenciasByBeneficio(beneficioId: number): Observable<Transferencia[]> {
    return this.http.get<Transferencia[]>(`${this.API_URL}/${beneficioId}/transferencias`);
  }
}
