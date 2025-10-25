import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of, throwError } from 'rxjs';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { BeneficiosComponent } from './beneficios.component';
import { BeneficioService } from '../../core/services/beneficio.service';
import { Beneficio } from '../../models/beneficio.model';

describe('BeneficiosComponent', () => {
  let component: BeneficiosComponent;
  let fixture: ComponentFixture<BeneficiosComponent>;
  let beneficioService: jasmine.SpyObj<BeneficioService>;

  const mockBeneficios: Beneficio[] = [
    { id: 1, nome: 'Beneficio 1', descricao: 'Desc 1', valor: 1000, ativo: true },
    { id: 2, nome: 'Beneficio 2', descricao: 'Desc 2', valor: 2000, ativo: true }
  ];

  beforeEach(async () => {
    const beneficioServiceSpy = jasmine.createSpyObj('BeneficioService', [
      'getAll', 'create', 'update', 'delete', 'transfer'
    ]);

    await TestBed.configureTestingModule({
      imports: [BeneficiosComponent, HttpClientTestingModule],
      providers: [
        { provide: BeneficioService, useValue: beneficioServiceSpy }
      ],
      schemas: [NO_ERRORS_SCHEMA]
    }).compileComponents();

    fixture = TestBed.createComponent(BeneficiosComponent);
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

  describe('criarBeneficio', () => {
    it('should create beneficio with valid data', () => {
      const novoBeneficio = { nome: 'Novo', descricao: 'Desc', valor: 1500, ativo: true };
      component.novoBeneficio = novoBeneficio;
      beneficioService.create.and.returnValue(of({ id: 3, ...novoBeneficio }));
      beneficioService.getAll.and.returnValue(of(mockBeneficios));

      component.criarBeneficio();

      expect(beneficioService.create).toHaveBeenCalledWith(novoBeneficio);
      expect(component.showForm).toBeFalse();
    });

    it('should not create beneficio with invalid data', () => {
      component.novoBeneficio = { nome: '', descricao: 'Desc', valor: 0, ativo: true };

      component.criarBeneficio();

      expect(component.error).toBe('Nome e valor são obrigatórios');
      expect(beneficioService.create).not.toHaveBeenCalled();
    });
  });

  describe('editarBeneficio', () => {
    it('should set beneficio for editing', () => {
      const beneficio = mockBeneficios[0];

      component.editarBeneficio(beneficio);

      expect(component.beneficioEditando).toEqual(beneficio);
      expect(component.showEditForm).toBeTrue();
    });
  });

  describe('salvarEdicao', () => {
    it('should update beneficio', () => {
      const beneficioEditado = { ...mockBeneficios[0], nome: 'Editado' };
      component.beneficioEditando = beneficioEditado;
      beneficioService.update.and.returnValue(of(beneficioEditado));
      beneficioService.getAll.and.returnValue(of(mockBeneficios));

      component.salvarEdicao();

      expect(beneficioService.update).toHaveBeenCalledWith(beneficioEditado.id!, beneficioEditado);
      expect(component.showEditForm).toBeFalse();
    });

    it('should not update if no beneficio is being edited', () => {
      component.beneficioEditando = null;

      component.salvarEdicao();

      expect(beneficioService.update).not.toHaveBeenCalled();
    });
  });

  describe('cancelarEdicao', () => {
    it('should cancel editing', () => {
      component.beneficioEditando = mockBeneficios[0];
      component.showEditForm = true;

      component.cancelarEdicao();

      expect(component.beneficioEditando).toBeNull();
      expect(component.showEditForm).toBeFalse();
    });
  });

  describe('excluirBeneficio', () => {
    it('should delete beneficio after confirmation', () => {
      spyOn(window, 'confirm').and.returnValue(true);
      beneficioService.delete.and.returnValue(of(undefined));
      beneficioService.getAll.and.returnValue(of(mockBeneficios));

      component.excluirBeneficio(mockBeneficios[0]);

      expect(beneficioService.delete).toHaveBeenCalledWith(mockBeneficios[0].id!);
    });

    it('should not delete beneficio if not confirmed', () => {
      spyOn(window, 'confirm').and.returnValue(false);

      component.excluirBeneficio(mockBeneficios[0]);

      expect(beneficioService.delete).not.toHaveBeenCalled();
    });
  });

  describe('iniciarTransferencia', () => {
    it('should start transfer process', () => {
      const beneficio = mockBeneficios[0];

      component.iniciarTransferencia(beneficio);

      expect(component.transferencia.fromId).toBe(beneficio.id!);
      expect(component.showTransferForm).toBeTrue();
    });
  });

  describe('realizarTransferencia', () => {
    it('should transfer with valid data', () => {
      component.transferencia = { fromId: 1, toId: 2, amount: 500 };
      beneficioService.transfer.and.returnValue(of('Success'));
      beneficioService.getAll.and.returnValue(of(mockBeneficios));

      component.realizarTransferencia();

      expect(beneficioService.transfer).toHaveBeenCalledWith(component.transferencia);
      expect(component.showTransferForm).toBeFalse();
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
  });

  describe('cancelarTransferencia', () => {
    it('should cancel transfer process', () => {
      component.transferencia = { fromId: 1, toId: 2, amount: 500 };
      component.showTransferForm = true;

      component.cancelarTransferencia();

      expect(component.transferencia).toEqual({ fromId: 0, toId: 0, amount: 0 });
      expect(component.showTransferForm).toBeFalse();
    });
  });

  describe('resetarFormulario', () => {
    it('should reset form data', () => {
      component.novoBeneficio = { nome: 'Test', descricao: 'Test', valor: 100, ativo: true };

      component.resetarFormulario();

      expect(component.novoBeneficio).toEqual({
        nome: '',
        descricao: '',
        valor: 0,
        ativo: true
      });
    });
  });

  describe('beneficiosDisponiveis getter', () => {
    it('should return beneficios excluding origin', () => {
      component.beneficios = mockBeneficios;
      component.transferencia.fromId = 1;

      const disponiveis = component.beneficiosDisponiveis;

      expect(disponiveis).toEqual([mockBeneficios[1]]);
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
