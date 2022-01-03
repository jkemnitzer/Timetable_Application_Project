import { TestBed } from '@angular/core/testing';
import { PermissionGuard } from './permission-guard';

describe('CanActivateGuard', () => {
  let service: PermissionGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PermissionGuard);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
