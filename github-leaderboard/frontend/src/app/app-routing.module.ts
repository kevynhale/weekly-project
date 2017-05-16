import { NgModule }     from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppComponent } from './app.component';

import { OrgComponent } from './pages/org/org.component'

@NgModule({
  imports: [
    RouterModule.forRoot([
      {
        path: '',
        redirectTo: '/org',
        pathMatch: 'full'
        
      },
      {
        path: 'org',
        component: OrgComponent,
      },
      {
        path: 'org/:org', 
        component: OrgComponent
      }
   	]
   	)],
   	exports: [
    RouterModule
  	]
  })
export class AppRouterModule {}