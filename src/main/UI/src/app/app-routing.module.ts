import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { LivePresentationComponent } from './live-presentation/live-presentation.component';

const routes: Routes = [
  { path: '', component: AppComponent },
  { path: 'live-presentations', component: LivePresentationComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
