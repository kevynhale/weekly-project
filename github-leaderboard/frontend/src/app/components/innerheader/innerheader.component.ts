import { Component, Input} from '@angular/core';
import { UserService } from '../../services/user.service'
import 'rxjs/add/operator/catch';


@Component({
  selector: 'app-innerheader',
  templateUrl: './innerheader.component.html',
  styleUrls: ['./innerheader.component.scss']
})
export class InnerHeaderComponent {
	@Input()
	org: String;
	orgImage: String;

	showResults: boolean;

	filter: String = "";

	orgs: String[];

	constructor(
		private userService: UserService) {
	}

	ngOnInit() {
		if (this.org == "undefined") {
			this.orgImage = "images/githubleaderboardicon.png"
		}
		else {

		}
		this.showResults = false;
		this.userService.getOrgs(this.filter)
    		.subscribe( orgs => this.setOrgs(orgs),
						err => console.log(err));
	}

	changeShow() {
		console.log('change')
		this.showResults = !this.showResults
	}

	filterOrg(filter) {
		this.filter = filter
		this.userService.getOrgs(this.filter)
    		.subscribe( orgs => this.setOrgs(orgs),
						err => console.log(err));

	}

	setOrgs(orgs) {
		this.orgs = orgs
	}

	checkImage(image)
	{
		console.log(image)
		if (image != null) {
			return true;
		}
		else {
			return false;
		}
	}

}
