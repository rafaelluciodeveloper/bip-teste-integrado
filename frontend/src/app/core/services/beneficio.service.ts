import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { Beneficio, TransferenciaRequest } from '../../models/beneficio.model';
import { ApiService } from './api.service';

/**
 * Serviço responsável pelas operações relacionadas aos benefícios.
 * Utiliza o ApiService para comunicação com o backend.
 * 
 * @author Rafael Lucio
 * @version 1.0
 * @since 1.0
 */
@Injectable({
  providedIn: 'root'
})
export class BeneficioService {
  private readonly apiService = inject(ApiService);

  /**
   * Busca todos os benefícios.
   * 
   * @returns Observable com lista de benefícios
   */
  getAll(): Observable<Beneficio[]> {
    return this.apiService.get<Beneficio[]>('/beneficios');
  }

  /**
   * Busca um benefício por ID.
   * 
   * @param id identificador do benefício
   * @returns Observable com o benefício
   */
  getById(id: number): Observable<Beneficio> {
    return this.apiService.get<Beneficio>(`/beneficios/${id}`);
  }

  /**
   * Cria um novo benefício.
   * 
   * @param beneficio dados do benefício
   * @returns Observable com o benefício criado
   */
  create(beneficio: Beneficio): Observable<Beneficio> {
    return this.apiService.post<Beneficio>('/beneficios', beneficio);
  }

  /**
   * Atualiza um benefício existente.
   * 
   * @param id identificador do benefício
   * @param beneficio dados atualizados
   * @returns Observable com o benefício atualizado
   */
  update(id: number, beneficio: Beneficio): Observable<Beneficio> {
    return this.apiService.put<Beneficio>(`/beneficios/${id}`, beneficio);
  }

  /**
   * Remove um benefício.
   * 
   * @param id identificador do benefício
   * @returns Observable vazio
   */
  delete(id: number): Observable<void> {
    return this.apiService.delete<void>(`/beneficios/${id}`);
  }

  /**
   * Realiza transferência entre benefícios.
   * 
   * @param transferencia dados da transferência
   * @returns Observable com mensagem de sucesso
   */
  transfer(transferencia: TransferenciaRequest): Observable<string> {
    return this.apiService.postText('/beneficios/transferir', transferencia);
  }

  /**
   * Busca benefícios ativos.
   * 
   * @returns Observable com lista de benefícios ativos
   */
  getAtivos(): Observable<Beneficio[]> {
    return this.apiService.get<Beneficio[]>('/beneficios/ativos');
  }

  /**
   * Busca benefícios por nome.
   * 
   * @param nome nome para busca
   * @returns Observable com lista de benefícios encontrados
   */
  search(nome: string): Observable<Beneficio[]> {
    return this.apiService.get<Beneficio[]>(`/beneficios/buscar?nome=${encodeURIComponent(nome)}`);
  }
}
