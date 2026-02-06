import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CashierPage } from './cashier-page';

describe('CashierPage', () => {
  let component: CashierPage;
  let fixture: ComponentFixture<CashierPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CashierPage]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CashierPage);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
