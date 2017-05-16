import {Injectable} from '@angular/core'
import { Http, Response, Headers, RequestOptions } from '@angular/http';

import 'rxjs/add/operator/map';

@Injectable()
export class UserService {

	base = "http://localhost:8050/leaderboard"

	constructor(private http: Http) {}


	getUser(org) {
		let url =  this.base + "/org/" + org
		let headers    = new Headers({'Content-Type': 'application/json'})
		let options    = new RequestOptions({ headers: headers })			
		return this.http.get(url, options)
			.map(response => response.json())
	}


}