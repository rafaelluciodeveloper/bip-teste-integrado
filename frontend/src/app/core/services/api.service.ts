import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

/**
 * Serviço base para comunicação com a API.
 * Centraliza configurações de URL e métodos HTTP.
 * 
 * @author Rafael Lucio
 * @version 1.0
 * @since 1.0
 */
@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private readonly baseUrl = 'http://localhost:8080/api/v1';
  private readonly http = inject(HttpClient);

  /**
   * Realiza requisição GET para a API.
   * 
   * @param endpoint endpoint da API
   * @returns Observable com a resposta
   */
  get<T>(endpoint: string): Observable<T> {
    return this.http.get<T>(`${this.baseUrl}${endpoint}`);
  }

  /**
   * Realiza requisição POST para a API.
   * 
   * @param endpoint endpoint da API
   * @param data dados a serem enviados
   * @returns Observable com a resposta
   */
  post<T>(endpoint: string, data: unknown): Observable<T> {
    return this.http.post<T>(`${this.baseUrl}${endpoint}`, data);
  }

  /**
   * Realiza requisição PUT para a API.
   * 
   * @param endpoint endpoint da API
   * @param data dados a serem enviados
   * @returns Observable com a resposta
   */
  put<T>(endpoint: string, data: unknown): Observable<T> {
    return this.http.put<T>(`${this.baseUrl}${endpoint}`, data);
  }

  /**
   * Realiza requisição DELETE para a API.
   * 
   * @param endpoint endpoint da API
   * @returns Observable com a resposta
   */
  delete<T>(endpoint: string): Observable<T> {
    return this.http.delete<T>(`${this.baseUrl}${endpoint}`);
  }
}
