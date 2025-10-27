import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TransferenciaService } from './transferencia.service';
import { Transferencia } from '../models/transferencia.model';

describe('TransferenciaService', () => {
  let service: TransferenciaService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [TransferenciaService]
    });
    service = TestBed.inject(TransferenciaService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('getHistoricoTransferencias', () => {
    it('should return list of all transferencias', () => {
      const mockTransferencias: Transferencia[] = [
        {
          id: 1,
          beneficioOrigem: { id: 1, nome: 'Origem', valor: 1000 },
          beneficioDestino: { id: 2, nome: 'Destino', valor: 2000 },
          valor: 500,
          dataTransferencia: '2023-01-01T10:00:00',
          observacoes: 'Teste'
        }
      ];

      service.getHistoricoTransferencias().subscribe((transferencias: Transferencia[]) => {
        expect(transferencias).toEqual(mockTransferencias);
      });

      const req = httpMock.expectOne('/api/v1/beneficios/transferencias/historico');
      expect(req.request.method).toBe('GET');
      req.flush(mockTransferencias);
    });
  });

  describe('getTransferenciasByBeneficio', () => {
    it('should return transferencias for specific beneficio', () => {
      const beneficioId: number = 1;
      const mockTransferencias: Transferencia[] = [
        {
          id: 1,
          beneficioOrigem: { id: 1, nome: 'Origem', valor: 1000 },
          beneficioDestino: { id: 2, nome: 'Destino', valor: 2000 },
          valor: 500,
          dataTransferencia: '2023-01-01T10:00:00',
          observacoes: 'Teste'
        }
      ];

      service.getTransferenciasByBeneficio(beneficioId).subscribe((transferencias: Transferencia[]) => {
        expect(transferencias).toEqual(mockTransferencias);
      });

      const req = httpMock.expectOne(`/api/v1/beneficios/${beneficioId}/transferencias`);
      expect(req.request.method).toBe('GET');
      req.flush(mockTransferencias);
    });
  });
});
