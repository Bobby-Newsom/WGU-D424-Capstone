import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { LivePresentation } from '../models/live-presentation.model';

@Injectable({
  providedIn: 'root'
})
export class LivePresentationService {
  private apiUrl = `${environment.apiUrl}/live-presentations`;

  constructor(private http: HttpClient) {}

  getLivePresentation(id: number): Observable<LivePresentation> {
    return this.http.get<LivePresentation>(`${this.apiUrl}/${id}`);
  }

  createLivePresentation(presentation: LivePresentation): Observable<LivePresentation> {
    return this.http.post<LivePresentation>(this.apiUrl, presentation);
  }

  updateLivePresentation(id: number, presentation: LivePresentation): Observable<LivePresentation> {
    return this.http.put<LivePresentation>(`${this.apiUrl}/${id}`, presentation);
  }

  deleteLivePresentation(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  searchLivePresentations(query: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/search`, { params: { query } });
  }
}
