import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { BeneficioListComponent } from './beneficio-list.component';
import { Beneficio } from '../../../models/beneficio.model';

describe('BeneficioListComponent', () => {
  let component: BeneficioListComponent;
  let fixture: ComponentFixture<BeneficioListComponent>;

  const mockBeneficios: Beneficio[] = [
    { id: 1, nome: 'Beneficio 1', descricao: 'Desc 1', valor: 1000, ativo: true },
    { id: 2, nome: 'Beneficio 2', descricao: 'Desc 2', valor: 2000, ativo: true }
  ];

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BeneficioListComponent],
      schemas: [NO_ERRORS_SCHEMA]
    }).compileComponents();

    fixture = TestBed.createComponent(BeneficioListComponent);
    component = fixture.componentInstance;
    component.beneficios = mockBeneficios;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should emit edit event when onEdit is called', () => {
    spyOn(component.edit, 'emit');
    const beneficio = mockBeneficios[0];

    component.onEdit(beneficio);

    expect(component.edit.emit).toHaveBeenCalledWith(beneficio);
  });

  it('should emit delete event when onDelete is called', () => {
    spyOn(component.delete, 'emit');
    const beneficio = mockBeneficios[0];

    component.onDelete(beneficio);

    expect(component.delete.emit).toHaveBeenCalledWith(beneficio);
  });

  it('should emit transfer event when onTransfer is called', () => {
    spyOn(component.transfer, 'emit');
    const beneficio = mockBeneficios[0];

    component.onTransfer(beneficio);

    expect(component.transfer.emit).toHaveBeenCalledWith(beneficio);
  });

  it('should have default values', () => {
    expect(component.beneficios).toEqual(mockBeneficios);
    expect(component.loading).toBeFalse();
    expect(component.showActions).toBeTrue();
  });

  it('should accept input values', () => {
    component.loading = true;
    component.showActions = false;

    expect(component.loading).toBeTrue();
    expect(component.showActions).toBeFalse();
  });
});
