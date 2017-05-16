import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-innerheader',
  templateUrl: './innerheader.component.html',
  styleUrls: ['./innerheader.component.scss']
})
export class InnerHeaderComponent {
	@Input()
	org: String;

}
