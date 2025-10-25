import { Transferencia } from './transferencia.model';

describe('Transferencia Model', () => {
  describe('Transferencia interface', () => {
    it('should create transferencia with required fields', () => {
      const transferencia: Transferencia = {
        id: 1,
        beneficioOrigem: {
          id: 1,
          nome: 'Beneficio Origem',
          valor: 1000
        },
        beneficioDestino: {
          id: 2,
          nome: 'Beneficio Destino',
          valor: 2000
        },
        valor: 500,
        dataTransferencia: '2023-01-01T10:00:00'
      };

      expect(transferencia.id).toBe(1);
      expect(transferencia.beneficioOrigem.id).toBe(1);
      expect(transferencia.beneficioOrigem.nome).toBe('Beneficio Origem');
      expect(transferencia.beneficioOrigem.valor).toBe(1000);
      expect(transferencia.beneficioDestino.id).toBe(2);
      expect(transferencia.beneficioDestino.nome).toBe('Beneficio Destino');
      expect(transferencia.beneficioDestino.valor).toBe(2000);
      expect(transferencia.valor).toBe(500);
      expect(transferencia.dataTransferencia).toBe('2023-01-01T10:00:00');
    });

    it('should create transferencia with optional observacoes', () => {
      const transferencia: Transferencia = {
        id: 1,
        beneficioOrigem: {
          id: 1,
          nome: 'Beneficio Origem',
          valor: 1000
        },
        beneficioDestino: {
          id: 2,
          nome: 'Beneficio Destino',
          valor: 2000
        },
        valor: 500,
        dataTransferencia: '2023-01-01T10:00:00',
        observacoes: 'Transferência de teste'
      };

      expect(transferencia.observacoes).toBe('Transferência de teste');
    });

    it('should allow observacoes to be undefined', () => {
      const transferencia: Transferencia = {
        id: 1,
        beneficioOrigem: {
          id: 1,
          nome: 'Beneficio Origem',
          valor: 1000
        },
        beneficioDestino: {
          id: 2,
          nome: 'Beneficio Destino',
          valor: 2000
        },
        valor: 500,
        dataTransferencia: '2023-01-01T10:00:00'
      };

      expect(transferencia.observacoes).toBeUndefined();
    });

    it('should handle zero values', () => {
      const transferencia: Transferencia = {
        id: 0,
        beneficioOrigem: {
          id: 0,
          nome: '',
          valor: 0
        },
        beneficioDestino: {
          id: 0,
          nome: '',
          valor: 0
        },
        valor: 0,
        dataTransferencia: ''
      };

      expect(transferencia.id).toBe(0);
      expect(transferencia.beneficioOrigem.id).toBe(0);
      expect(transferencia.beneficioOrigem.nome).toBe('');
      expect(transferencia.beneficioOrigem.valor).toBe(0);
      expect(transferencia.beneficioDestino.id).toBe(0);
      expect(transferencia.beneficioDestino.nome).toBe('');
      expect(transferencia.beneficioDestino.valor).toBe(0);
      expect(transferencia.valor).toBe(0);
      expect(transferencia.dataTransferencia).toBe('');
    });
  });
});
