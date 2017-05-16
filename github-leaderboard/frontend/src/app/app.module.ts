import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { InnerHeaderComponent } from './components/innerheader/innerheader.component'
import { MarketingComponent } from './components/marketing/marketing.component'
import { UserUtilityComponent } from './components/userutility/userutility.component'
import { TableHeaderComponent } from './components/tableheader/tableheader.component'
import { TableRowsComponent } from './components/tablerows/tablerows.component'

@NgModule({
  declarations: [
    AppComponent,
    InnerHeaderComponent,
    MarketingComponent,
    UserUtilityComponent,
    TableHeaderComponent,
    TableRowsComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
