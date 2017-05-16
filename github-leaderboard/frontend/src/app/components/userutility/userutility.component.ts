import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-userutility',
  templateUrl: './userutility.component.html',
  styleUrls: ['./userutility.component.scss']
})
export class UserUtilityComponent {
	@Input()
	org: String;
}
