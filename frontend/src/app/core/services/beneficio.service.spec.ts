import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { BeneficioService } from './beneficio.service';
import { ApiService } from './api.service';
import { Beneficio , TransferenciaRequest } from '../../models/beneficio.model';

describe('BeneficioService', () => {
  let service: BeneficioService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [BeneficioService, ApiService]
    });
    service = TestBed.inject(BeneficioService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('getAll', () => {
    it('should return list of beneficios', () => {
      const mockBeneficios: Beneficio[] = [
        { id: 1, nome: 'Beneficio 1', descricao: 'Desc 1', valor: 1000, ativo: true },
        { id: 2, nome: 'Beneficio 2', descricao: 'Desc 2', valor: 2000, ativo: true }
      ];

      service.getAll().subscribe(beneficios => {
        expect(beneficios).toEqual(mockBeneficios);
      });

      const req = httpMock.expectOne('http://localhost:8080/api/v1/beneficios');
      expect(req.request.method).toBe('GET');
      req.flush(mockBeneficios);
    });
  });

  describe('getById', () => {
    it('should return beneficio by id', () => {
      const mockBeneficio: Beneficio = { id: 1, nome: 'Beneficio 1', descricao: 'Desc 1', valor: 1000, ativo: true };

      service.getById(1).subscribe(beneficio => {
        expect(beneficio).toEqual(mockBeneficio);
      });

      const req = httpMock.expectOne('http://localhost:8080/api/v1/beneficios/1');
      expect(req.request.method).toBe('GET');
      req.flush(mockBeneficio);
    });
  });

  describe('create', () => {
    it('should create new beneficio', () => {
      const newBeneficio: Beneficio = { nome: 'Novo Beneficio', descricao: 'Nova Desc', valor: 1500, ativo: true };
      const createdBeneficio: Beneficio = { id: 3, ...newBeneficio };

      service.create(newBeneficio).subscribe(beneficio => {
        expect(beneficio).toEqual(createdBeneficio);
      });

      const req = httpMock.expectOne('http://localhost:8080/api/v1/beneficios');
      expect(req.request.method).toBe('POST');
      expect(req.request.body).toEqual(newBeneficio);
      req.flush(createdBeneficio);
    });
  });

  describe('update', () => {
    it('should update existing beneficio', () => {
      const updatedBeneficio: Beneficio = { id: 1, nome: 'Beneficio Atualizado', descricao: 'Desc Atualizada', valor: 2000, ativo: true };

      service.update(1, updatedBeneficio).subscribe(beneficio => {
        expect(beneficio).toEqual(updatedBeneficio);
      });

      const req = httpMock.expectOne('http://localhost:8080/api/v1/beneficios/1');
      expect(req.request.method).toBe('PUT');
      expect(req.request.body).toEqual(updatedBeneficio);
      req.flush(updatedBeneficio);
    });
  });

  describe('delete', () => {
    it('should delete beneficio', () => {
      service.delete(1).subscribe();

      const req = httpMock.expectOne('http://localhost:8080/api/v1/beneficios/1');
      expect(req.request.method).toBe('DELETE');
      req.flush({});
    });
  });

  describe('transfer', () => {
    it('should transfer between beneficios', () => {
      const transferencia: TransferenciaRequest = { fromId: 1, toId: 2, amount: 500 };
      const response = 'Transferência realizada com sucesso';

      service.transfer(transferencia).subscribe(result => {
        expect(result).toBe(response);
      });

      const req = httpMock.expectOne('http://localhost:8080/api/v1/beneficios/transferir');
      expect(req.request.method).toBe('POST');
      expect(req.request.body).toEqual(transferencia);
      req.flush(response);
    });
  });

  describe('getAtivos', () => {
    it('should return active beneficios', () => {
      const mockBeneficios: Beneficio[] = [
        { id: 1, nome: 'Beneficio Ativo 1', descricao: 'Desc 1', valor: 1000, ativo: true }
      ];

      service.getAtivos().subscribe(beneficios => {
        expect(beneficios).toEqual(mockBeneficios);
      });

      const req = httpMock.expectOne('http://localhost:8080/api/v1/beneficios/ativos');
      expect(req.request.method).toBe('GET');
      req.flush(mockBeneficios);
    });
  });

  describe('search', () => {
    it('should search beneficios by name', () => {
      const searchTerm = 'teste';
      const mockBeneficios: Beneficio[] = [
        { id: 1, nome: 'Beneficio Teste', descricao: 'Desc 1', valor: 1000, ativo: true }
      ];

      service.search(searchTerm).subscribe(beneficios => {
        expect(beneficios).toEqual(mockBeneficios);
      });

      const req = httpMock.expectOne(`http://localhost:8080/api/v1/beneficios/buscar?nome=${encodeURIComponent(searchTerm)}`);
      expect(req.request.method).toBe('GET');
      req.flush(mockBeneficios);
    });
  });
});
