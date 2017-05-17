import { Component, Input} from '@angular/core';


@Component({
  selector: 'app-innerheader',
  templateUrl: './innerheader.component.html',
  styleUrls: ['./innerheader.component.scss']
})
export class InnerHeaderComponent {
	@Input()
	org: String;

	showResults: boolean;

	ngOnInit() {
		this.showResults = false;
	}

	changeShow() {
		console.log('change')
		this.showResults = !this.showResults
	}

}
