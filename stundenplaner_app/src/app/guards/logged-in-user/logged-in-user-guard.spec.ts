import { TestBed } from '@angular/core/testing';
import { LoggedInUserGuard } from './logged-in-user-guard';

describe('CanActivateGuard', () => {
  let service: LoggedInUserGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoggedInUserGuard);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
