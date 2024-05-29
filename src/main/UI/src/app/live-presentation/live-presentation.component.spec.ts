import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LivePresentationComponent } from './live-presentation.component';

describe('LivePresentationComponent', () => {
  let component: LivePresentationComponent;
  let fixture: ComponentFixture<LivePresentationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LivePresentationComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LivePresentationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
