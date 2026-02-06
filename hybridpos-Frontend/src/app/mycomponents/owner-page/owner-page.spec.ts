import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OwnerPage } from './owner-page';

describe('OwnerPage', () => {
  let component: OwnerPage;
  let fixture: ComponentFixture<OwnerPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OwnerPage]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OwnerPage);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
