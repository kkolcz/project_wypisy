import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrescriptionAddComponent } from './prescription-add.component';

describe('PrescriptionAddComponent', () => {
  let component: PrescriptionAddComponent;
  let fixture: ComponentFixture<PrescriptionAddComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PrescriptionAddComponent]
    });
    fixture = TestBed.createComponent(PrescriptionAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
