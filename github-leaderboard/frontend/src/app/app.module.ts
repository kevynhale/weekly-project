import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { OrgComponent } from './pages/org/org.component'

import { AppComponent } from './app.component';
import { InnerHeaderComponent } from './components/innerheader/innerheader.component'
import { MarketingComponent } from './components/marketing/marketing.component'
import { UserUtilityComponent } from './components/userutility/userutility.component'
import { TableHeaderComponent } from './components/tableheader/tableheader.component'
import { TableRowsComponent } from './components/tablerows/tablerows.component'
import { DropdownComponent } from './components/dropdown/dropdown.component'

import { UserService } from './services/user.service'

import { AppRouterModule } from './app-routing.module'

@NgModule({
  declarations: [
    AppComponent,
    InnerHeaderComponent,
    MarketingComponent,
    UserUtilityComponent,  
    TableHeaderComponent,
    TableRowsComponent,
    OrgComponent,
    DropdownComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AppRouterModule
  ],
  providers: [UserService],
  bootstrap: [AppComponent]
})
export class AppModule { }
