import { ComponentFixture, TestBed, fakeAsync, flush } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of, throwError } from 'rxjs';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { HistoricoComponent } from './historico.component';
import { TransferenciaService } from '../../core/services/transferencia.service';
import { Transferencia } from '../../core/models/transferencia.model';

describe('HistoricoComponent', () => {
  let component: HistoricoComponent;
  let fixture: ComponentFixture<HistoricoComponent>;
  let transferenciaService: jasmine.SpyObj<TransferenciaService>;

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

  beforeEach(async () => {
    const transferenciaServiceSpy = jasmine.createSpyObj('TransferenciaService', [
      'getHistoricoTransferencias'
    ]);

    await TestBed.configureTestingModule({
      imports: [HistoricoComponent, HttpClientTestingModule],
      providers: [
        { provide: TransferenciaService, useValue: transferenciaServiceSpy }
      ],
      schemas: [NO_ERRORS_SCHEMA]
    }).compileComponents();

    fixture = TestBed.createComponent(HistoricoComponent);
    component = fixture.componentInstance;
    transferenciaService = TestBed.inject(TransferenciaService) as jasmine.SpyObj<TransferenciaService>;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit', () => {
    it('should load transferencias on init', () => {
      transferenciaService.getHistoricoTransferencias.and.returnValue(of(mockTransferencias));

      component.ngOnInit();

      expect(transferenciaService.getHistoricoTransferencias).toHaveBeenCalled();
      expect(component.transferencias).toEqual(mockTransferencias);
      expect(component.loading).toBeFalse();
    });

    it('should handle error when loading transferencias', () => {
      transferenciaService.getHistoricoTransferencias.and.returnValue(throwError(() => new Error('Network error')));

      component.ngOnInit();

      expect(component.error).toBe('Erro ao carregar histórico de transferências');
      expect(component.loading).toBeFalse();
    });
  });


  describe('formatarData', () => {
    it('should format date string to Brazilian locale', () => {
      const dataString = '2023-01-01T10:00:00';
      const formatted = component.formatarData(dataString);

      expect(formatted).toContain('01/01/2023');
    });
  });

  describe('formatarValor', () => {
    it('should format number to Brazilian currency', () => {
      const valor = 1000.50;
      const formatted = component.formatarValor(valor);

      expect(formatted).toContain('R$');
      expect(formatted).toContain('1.000,50');
    });

    it('should format zero value', () => {
      const valor = 0;
      const formatted = component.formatarValor(valor);

      expect(formatted).toContain('R$');
      expect(formatted).toContain('0,00');
    });
  });
});
