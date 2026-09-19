import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CashRegisters } from './cash-registers';

describe('CashRegisters', () => {
  let component: CashRegisters;
  let fixture: ComponentFixture<CashRegisters>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CashRegisters]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CashRegisters);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
