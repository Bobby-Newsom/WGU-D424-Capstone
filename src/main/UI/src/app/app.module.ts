import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { AppRoutingModule } from './app-routing.module';  // Import the routing module

import { AppComponent } from './app.component';
import { LivePresentationComponent } from './live-presentation/live-presentation.component';
import { LivePresentationService } from './services/live-presentation.service';
import {RouterOutlet} from "@angular/router";


@NgModule({
  declarations: [
    AppComponent,
    LivePresentationComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    RouterOutlet
  ],
  providers: [LivePresentationService],
  bootstrap: [AppComponent]
})
export class AppModule { }
