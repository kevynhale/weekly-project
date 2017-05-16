import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { UserService } from '../../services/user.service'

@Component({
  selector: 'app-org',
  templateUrl: './org.component.html',
  styleUrls: ['./org.component.scss']
})
export class OrgComponent {

	private subscription:any;
	private id: string;

	constructor(
		private route: ActivatedRoute,
		private router: Router,
		private userService: UserService) {
	}

	ngOnInit() {
		this.subscription = this.route.params.subscribe(params => {
			this.id = String(params['org'])
		})
	}

}
