import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { LivePresentationService } from '../services/live-presentation.service';
import {LivePresentation} from "../models/live-presentation.model";

@Component({
  selector: 'app-live-presentation',
  templateUrl: './live-presentation.component.html',
  styleUrls: ['./live-presentation.component.css']
})
export class LivePresentationComponent implements OnInit {
  searchForm: FormGroup;
  createForm: FormGroup;
  updateForm: FormGroup;
  results: any[] = [];
  timestamp?: string;
  selectedPresentation?: LivePresentation;

  constructor(private livePresentationService: LivePresentationService) {
    this.searchForm = new FormGroup({
      query: new FormControl('')
    });

    this.createForm = new FormGroup({
      title: new FormControl(''),
      description: new FormControl(''),
      dateTime: new FormControl(''),
      presenter: new FormControl('')
    });

    this.updateForm = new FormGroup({
      id: new FormControl(''),
      title: new FormControl(''),
      description: new FormControl(''),
      dateTime: new FormControl(''),
      presenter: new FormControl('')
    });
  }

  ngOnInit(): void {}

  searchLivePresentations() {
    const query = this.searchForm.value.query;
    this.livePresentationService.searchLivePresentations(query).subscribe(response => {
      this.results = response.results;
      this.timestamp = response.timestamp;
    });
  }

  createLivePresentation() {
    const newPresentation = this.createForm.value;
    this.livePresentationService.createLivePresentation(newPresentation).subscribe(response => {
      this.createForm.reset();
      this.searchLivePresentations();  // Refresh the list
    });
  }

  selectPresentation(presentation: LivePresentation) {
    this.selectedPresentation = presentation;
    this.updateForm.setValue({
      id: presentation.id,
      title: presentation.title,
      description: presentation.description,
      dateTime: presentation.dateTime,
      presenter: presentation.presenter
    });
  }

  updateLivePresentation() {
    const updatedPresentation = this.updateForm.value;
    this.livePresentationService.updateLivePresentation(updatedPresentation.id, updatedPresentation).subscribe(response => {
      this.updateForm.reset();
      this.selectedPresentation = undefined;
      this.searchLivePresentations();  // Refresh the list
    });
  }

  deleteLivePresentation(id: number) {
    this.livePresentationService.deleteLivePresentation(id).subscribe(() => {
      this.searchLivePresentations();  // Refresh the list
    });
  }
}
