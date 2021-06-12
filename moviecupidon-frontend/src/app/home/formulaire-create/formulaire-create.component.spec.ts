import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormulaireCreateComponent } from './formulaire-create.component';

describe('FormulaireCreateComponent', () => {
  let component: FormulaireCreateComponent;
  let fixture: ComponentFixture<FormulaireCreateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FormulaireCreateComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FormulaireCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  xit('should create', () => {
    expect(component).toBeTruthy();
  });
});
