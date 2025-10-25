import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { BeneficioCardComponent } from './beneficio-card.component';
import { Beneficio } from '../../../models/beneficio.model';

describe('BeneficioCardComponent', () => {
  let component: BeneficioCardComponent;
  let fixture: ComponentFixture<BeneficioCardComponent>;

  const mockBeneficio: Beneficio = {
    id: 1,
    nome: 'Teste Beneficio',
    descricao: 'Descrição do benefício',
    valor: 1000,
    ativo: true
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BeneficioCardComponent],
      schemas: [NO_ERRORS_SCHEMA]
    }).compileComponents();

    fixture = TestBed.createComponent(BeneficioCardComponent);
    component = fixture.componentInstance;
    component.beneficio = mockBeneficio;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should emit edit event when onEdit is called', () => {
    spyOn(component.edit, 'emit');

    component.onEdit();

    expect(component.edit.emit).toHaveBeenCalledWith(mockBeneficio);
  });

  it('should emit delete event when onDelete is called', () => {
    spyOn(component.delete, 'emit');

    component.onDelete();

    expect(component.delete.emit).toHaveBeenCalledWith(mockBeneficio);
  });

  it('should emit transfer event when onTransfer is called', () => {
    spyOn(component.transfer, 'emit');

    component.onTransfer();

    expect(component.transfer.emit).toHaveBeenCalledWith(mockBeneficio);
  });

  it('should have showActions as true by default', () => {
    expect(component.showActions).toBeTrue();
  });

  it('should accept showActions input', () => {
    component.showActions = false;
    expect(component.showActions).toBeFalse();
  });
});
