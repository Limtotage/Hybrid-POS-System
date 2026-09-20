import { TestBed } from '@angular/core/testing';

import { IndexedDb } from './indexed-db';

describe('IndexedDb', () => {
  let service: IndexedDb;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IndexedDb);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
