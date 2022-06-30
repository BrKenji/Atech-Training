import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GraphsComponent } from './graphs/graphs.component';
import { GreetingComponent } from './greeting/greeting.component';
import { HistoryComponent } from './history/history.component';
import { HomeComponent } from './home/home.component';
import { SendFileComponent } from './send-file/send-file.component';

const routes: Routes = [
  {path: 'greeting', component: GreetingComponent},
  {path: 'home', component: HomeComponent},
  {path: 'send-file', component: SendFileComponent},
  {path: 'history', component: HistoryComponent},
  {path: 'graphs', component: GraphsComponent},
  {path: '', component: HomeComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
