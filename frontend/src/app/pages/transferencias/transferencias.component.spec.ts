import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of, throwError } from 'rxjs';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { TransferenciasComponent } from './transferencias.component';
import { BeneficioService } from '../../core/services/beneficio.service';
import { Beneficio, TransferenciaRequest } from '../../models/beneficio.model';

describe('TransferenciasComponent', () => {
  let component: TransferenciasComponent;
  let fixture: ComponentFixture<TransferenciasComponent>;
  let beneficioService: jasmine.SpyObj<BeneficioService>;

  const mockBeneficios: Beneficio[] = [
    { id: 1, nome: 'Beneficio 1', descricao: 'Desc 1', valor: 1000, ativo: true },
    { id: 2, nome: 'Beneficio 2', descricao: 'Desc 2', valor: 2000, ativo: true },
    { id: 3, nome: 'Beneficio 3', descricao: 'Desc 3', valor: 3000, ativo: false }
  ];

  beforeEach(async () => {
    const beneficioServiceSpy = jasmine.createSpyObj('BeneficioService', [
      'getAll', 'transfer'
    ]);

    await TestBed.configureTestingModule({
      imports: [TransferenciasComponent, HttpClientTestingModule],
      providers: [
        { provide: BeneficioService, useValue: beneficioServiceSpy }
      ],
      schemas: [NO_ERRORS_SCHEMA]
    }).compileComponents();

    fixture = TestBed.createComponent(TransferenciasComponent);
    component = fixture.componentInstance;
    beneficioService = TestBed.inject(BeneficioService) as jasmine.SpyObj<BeneficioService>;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit', () => {
    it('should load beneficios on init', () => {
      beneficioService.getAll.and.returnValue(of(mockBeneficios));

      component.ngOnInit();

      expect(beneficioService.getAll).toHaveBeenCalled();
      expect(component.beneficios).toEqual(mockBeneficios);
      expect(component.loading).toBeFalse();
    });

    it('should handle error when loading beneficios', () => {
      const errorMessage = 'Network error';
      beneficioService.getAll.and.returnValue(throwError(() => new Error(errorMessage)));

      component.ngOnInit();

      expect(component.error).toContain(errorMessage);
      expect(component.loading).toBeFalse();
    });
  });

  describe('carregarBeneficios', () => {
    it('should load beneficios and set loading state', () => {
      beneficioService.getAll.and.returnValue(of(mockBeneficios));

      component.carregarBeneficios();

      expect(component.loading).toBeTrue();
      expect(component.error).toBeNull();
      expect(beneficioService.getAll).toHaveBeenCalled();
    });
  });

  describe('realizarTransferencia', () => {
    it('should transfer with valid data', () => {
      component.transferencia = { fromId: 1, toId: 2, amount: 500 };
      beneficioService.transfer.and.returnValue(of('Success'));
      beneficioService.getAll.and.returnValue(of(mockBeneficios));

      component.realizarTransferencia();

      expect(beneficioService.transfer).toHaveBeenCalledWith(component.transferencia);
      expect(component.success).toBe('Transferência realizada com sucesso!');
      expect(component.loading).toBeFalse();
    });

    it('should not transfer with same origin and destination', () => {
      component.transferencia = { fromId: 1, toId: 1, amount: 500 };

      component.realizarTransferencia();

      expect(component.error).toBe('Benefício origem e destino não podem ser iguais');
      expect(beneficioService.transfer).not.toHaveBeenCalled();
    });

    it('should not transfer with invalid amount', () => {
      component.transferencia = { fromId: 1, toId: 2, amount: 0 };

      component.realizarTransferencia();

      expect(component.error).toBe('Valor deve ser positivo');
      expect(beneficioService.transfer).not.toHaveBeenCalled();
    });

    it('should handle transfer error', () => {
      component.transferencia = { fromId: 1, toId: 2, amount: 500 };
      const errorMessage = 'Transfer error';
      beneficioService.transfer.and.returnValue(throwError(() => new Error(errorMessage)));

      component.realizarTransferencia();

      expect(component.error).toContain(errorMessage);
      expect(component.loading).toBeFalse();
    });
  });

  describe('resetarFormulario', () => {
    it('should reset form data', () => {
      component.transferencia = { fromId: 1, toId: 2, amount: 500 };

      component.resetarFormulario();

      expect(component.transferencia).toEqual({
        fromId: 0,
        toId: 0,
        amount: 0
      });
    });
  });

  describe('beneficiosOrigem getter', () => {
    it('should return only active beneficios', () => {
      component.beneficios = mockBeneficios;

      const beneficiosOrigem = component.beneficiosOrigem;

      expect(beneficiosOrigem).toEqual([mockBeneficios[0], mockBeneficios[1]]);
    });
  });

  describe('beneficiosDestino getter', () => {
    it('should return active beneficios excluding origin', () => {
      component.beneficios = mockBeneficios;
      component.transferencia.fromId = 1;

      const beneficiosDestino = component.beneficiosDestino;

      expect(beneficiosDestino).toEqual([mockBeneficios[1]]);
    });
  });

  describe('beneficioOrigem getter', () => {
    it('should return selected origin beneficio', () => {
      component.beneficios = mockBeneficios;
      component.transferencia.fromId = 1;

      const beneficioOrigem = component.beneficioOrigem;

      expect(beneficioOrigem).toEqual(mockBeneficios[0]);
    });

    it('should return null if no origin selected', () => {
      component.beneficios = mockBeneficios;
      component.transferencia.fromId = 0;

      const beneficioOrigem = component.beneficioOrigem;

      expect(beneficioOrigem).toBeNull();
    });
  });

  describe('beneficioDestino getter', () => {
    it('should return selected destination beneficio', () => {
      component.beneficios = mockBeneficios;
      component.transferencia.toId = 2;

      const beneficioDestino = component.beneficioDestino;

      expect(beneficioDestino).toEqual(mockBeneficios[1]);
    });

    it('should return null if no destination selected', () => {
      component.beneficios = mockBeneficios;
      component.transferencia.toId = 0;

      const beneficioDestino = component.beneficioDestino;

      expect(beneficioDestino).toBeNull();
    });
  });

  describe('ngOnDestroy', () => {
    it('should unsubscribe from subscriptions', () => {
      spyOn(component['subscriptions'], 'unsubscribe');

      component.ngOnDestroy();

      expect(component['subscriptions'].unsubscribe).toHaveBeenCalled();
    });
  });
});
