import { Beneficio, TransferenciaRequest } from './beneficio.model';

describe('Beneficio Model', () => {
  describe('Beneficio interface', () => {
    it('should create beneficio with required fields', () => {
      const beneficio: Beneficio = {
        nome: 'Teste Beneficio',
        valor: 1000
      };

      expect(beneficio.nome).toBe('Teste Beneficio');
      expect(beneficio.valor).toBe(1000);
    });

    it('should create beneficio with all fields', () => {
      const beneficio: Beneficio = {
        id: 1,
        nome: 'Teste Beneficio',
        descricao: 'Descrição do benefício',
        valor: 1000,
        ativo: true,
        version: 1
      };

      expect(beneficio.id).toBe(1);
      expect(beneficio.nome).toBe('Teste Beneficio');
      expect(beneficio.descricao).toBe('Descrição do benefício');
      expect(beneficio.valor).toBe(1000);
      expect(beneficio.ativo).toBeTrue();
      expect(beneficio.version).toBe(1);
    });

    it('should allow optional fields to be undefined', () => {
      const beneficio: Beneficio = {
        nome: 'Teste Beneficio',
        valor: 1000
      };

      expect(beneficio.id).toBeUndefined();
      expect(beneficio.descricao).toBeUndefined();
      expect(beneficio.ativo).toBeUndefined();
      expect(beneficio.version).toBeUndefined();
    });
  });

  describe('TransferenciaRequest interface', () => {
    it('should create transferencia request with all fields', () => {
      const transferencia: TransferenciaRequest = {
        fromId: 1,
        toId: 2,
        amount: 500
      };

      expect(transferencia.fromId).toBe(1);
      expect(transferencia.toId).toBe(2);
      expect(transferencia.amount).toBe(500);
    });

    it('should allow zero values', () => {
      const transferencia: TransferenciaRequest = {
        fromId: 0,
        toId: 0,
        amount: 0
      };

      expect(transferencia.fromId).toBe(0);
      expect(transferencia.toId).toBe(0);
      expect(transferencia.amount).toBe(0);
    });
  });
});
