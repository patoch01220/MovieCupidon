import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormulaireJoinComponent } from './formulaire-join.component';

describe('FormulaireJoinComponent', () => {
  let component: FormulaireJoinComponent;
  let fixture: ComponentFixture<FormulaireJoinComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FormulaireJoinComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FormulaireJoinComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  xit('should create', () => {
    expect(component).toBeTruthy();
  });
});
