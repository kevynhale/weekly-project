import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-dropdown',
  templateUrl: './dropdown.component.html',
  styleUrls: ['./dropdown.component.scss']
})
export class DropdownComponent {
	@Input()
	show: boolean;

	@Input()
	criteria: String;

	@Input()
	results: any;


	ngOnInit() {
		
	}

	getDisabled() {
		if (this.results != null) {
			console.log(this.criteria, "set disabled")
			return this.results.filter(e => e.name == this.criteria).length > 0
		}
	}

	checkImage(image:string)
	{
		if (image != null || image != "") {
			return true;
		}
		else {
			return false;
		}
	}
}
