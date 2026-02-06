import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Cashiers } from './cashiers';

describe('Cashiers', () => {
  let component: Cashiers;
  let fixture: ComponentFixture<Cashiers>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Cashiers]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Cashiers);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
