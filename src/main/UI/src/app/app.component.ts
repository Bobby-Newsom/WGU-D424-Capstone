import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LivePresentationService } from './services/live-presentation.service';
import { LivePresentation } from './models/live-presentation.model';
import { environment } from '../environments/environment';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  englishMessage$!: Observable<string>;
  frenchMessage$!: Observable<string>;
  presentationAnnouncement$!: Observable<string>;
  timestamp!: string;
  results: LivePresentation[] = [];
  selectedPresentation: LivePresentation | null = null;

  searchForm: FormGroup = new FormGroup({
    query: new FormControl('', Validators.required)
  });

  createForm: FormGroup = new FormGroup({
    title: new FormControl('', Validators.required),
    description: new FormControl('', [Validators.required, Validators.maxLength(500)]),
    dateTime: new FormControl('', Validators.required),
    presenter: new FormControl('', Validators.required)
  });

  updateForm: FormGroup = new FormGroup({
    title: new FormControl('', Validators.required),
    description: new FormControl('', [Validators.required, Validators.maxLength(500)]),
    dateTime: new FormControl('', Validators.required),
    presenter: new FormControl('', Validators.required)
  });

  constructor(private httpClient: HttpClient, private livePresentationService: LivePresentationService) {}

  private baseURL: string = environment.apiUrl;
  private getUrl: string = this.baseURL + '/room/reservation/v1/';
  private postUrl: string = this.baseURL + '/room/reservation/v1';
  public submitted!: boolean;
  roomsearch!: FormGroup;
  rooms!: Room[];
  request!: ReserveRoomRequest;
  currentCheckInVal!: string;
  currentCheckOutVal!: string;

  ngOnInit() {
    this.englishMessage$ = this.httpClient.get(this.baseURL + '/api/welcome/en/', { responseType: 'text' });
    this.frenchMessage$ = this.httpClient.get(this.baseURL + '/api/welcome/fr/', { responseType: 'text' });
    this.presentationAnnouncement$ = this.httpClient.get(this.baseURL + '/api/presentation/', { responseType: 'text' });

    this.roomsearch = new FormGroup({
      checkin: new FormControl(' '),
      checkout: new FormControl(' ')
    });

    const roomsearchValueChanges$ = this.roomsearch.valueChanges;
    roomsearchValueChanges$.subscribe(x => {
      this.currentCheckInVal = x.checkin;
      this.currentCheckOutVal = x.checkout;
    });
  }

  onSubmit({ value, valid }: { value: Roomsearch; valid: boolean }) {
    this.getAll().subscribe(rooms => {
      this.rooms = rooms;
    });
  }

  reserveRoom(value: string) {
    this.request = new ReserveRoomRequest(value, this.currentCheckInVal, this.currentCheckOutVal);
    this.createReservation(this.request);
  }

  createReservation(body: ReserveRoomRequest) {
    const options = {
      headers: new HttpHeaders().append('Content-Type', 'application/json')
    };

    this.httpClient.post(this.postUrl, body, options).subscribe(res => console.log(res));
  }

  getAll(): Observable<Room[]> {
    return this.httpClient.get<Room[]>(this.baseURL + '/room/reservation/v1?checkin=' + this.currentCheckInVal + '&checkout=' + this.currentCheckOutVal);
  }

  searchLivePresentations() {
    if (this.searchForm.invalid) {
      return;
    }
    const query = this.searchForm.get('query')?.value;
    this.livePresentationService.searchLivePresentations(query).subscribe(
      (response) => {
        this.results = response.results;
        this.timestamp = response.timestamp;
      },
      (error) => {
        console.error('Error searching live presentations', error);
      }
    );
  }

  createLivePresentation() {
    if (this.createForm.valid) {
      const newPresentation = this.createForm.value;
      this.livePresentationService.createLivePresentation(newPresentation).subscribe(
        (presentation: LivePresentation) => {
          this.results.push(presentation);
          this.createForm.reset();
        },
        (error) => {
          console.error('Error creating live presentation', error);
        }
      );
    }
  }

  updateLivePresentation() {
    if (this.updateForm.valid && this.selectedPresentation) {
      const updatedPresentation = this.updateForm.value;
      this.livePresentationService.updateLivePresentation(this.selectedPresentation.id, updatedPresentation).subscribe(
        (presentation: LivePresentation) => {
          const index = this.results.findIndex(p => p.id === this.selectedPresentation!.id);
          if (index !== -1) {
            this.results[index] = presentation;
          }
          this.selectedPresentation = null;
          this.updateForm.reset();
        },
        (error) => {
          console.error('Error updating live presentation', error);
        }
      );
    }
  }

  deleteLivePresentation(id: number) {
    if (confirm('Are you sure you want to delete this presentation?')) {
      this.livePresentationService.deleteLivePresentation(id).subscribe(
        () => {
          this.results = this.results.filter(p => p.id !== id);
          alert('Presentation deleted successfully.');
        },
        (error) => {
          console.error('Error deleting live presentation', error);
        }
      );
    }
  }

  selectPresentation(presentation: LivePresentation) {
    this.selectedPresentation = presentation;
    this.updateForm.setValue({
      title: presentation.title,
      description: presentation.description,
      dateTime: presentation.dateTime,
      presenter: presentation.presenter
    });
  }
}

export interface Roomsearch {
  checkin: string;
  checkout: string;
}

export interface Room {
  id: string;
  roomNumber: string;
  price: string;
  links: string;
}

export class ReserveRoomRequest {
  roomId: string;
  checkin: string;
  checkout: string;

  constructor(roomId: string, checkin: string, checkout: string) {
    this.roomId = roomId;
    this.checkin = checkin;
    this.checkout = checkout;
  }
}
