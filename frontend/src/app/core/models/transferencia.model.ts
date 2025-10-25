/**
 * Modelo que representa uma transferência entre benefícios.
 * 
 * @author Rafael Lucio
 * @version 1.0
 * @since 1.0
 */
export interface Transferencia {
  id: number;
  beneficioOrigem: {
    id: number;
    nome: string;
    valor: number;
  };
  beneficioDestino: {
    id: number;
    nome: string;
    valor: number;
  };
  valor: number;
  dataTransferencia: string;
  observacoes?: string;
}
