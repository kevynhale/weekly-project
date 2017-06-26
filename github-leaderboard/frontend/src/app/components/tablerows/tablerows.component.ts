import { Component, Input } from '@angular/core';

import { UserService } from '../../services/user.service'
import 'rxjs/add/operator/catch';

@Component({
  selector: 'app-tablerows',
  templateUrl: './tablerows.component.html',
  styleUrls: ['./tablerows.component.scss']
})
export class TableRowsComponent {
	@Input()
	org: String;
	users: any;

	constructor(
		private userService: UserService) {
	}

	ngOnChanges() {
		if (this.org != "undefined") {
    		this.userService.getUsers(this.org)
    			.subscribe( post => this.setUser(post),
						err => console.log(err));
    	}
	}

	setUser(user) {
		this.users = user.users
		console.log(user)
	}
}
