import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-tableheader',
  templateUrl: './tableheader.component.html',
  styleUrls: ['./tableheader.component.scss']
})
export class TableHeaderComponent {
	@Input()
	org: String;
}
