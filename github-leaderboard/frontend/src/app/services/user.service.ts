import {Injectable} from '@angular/core'
import { Http, Response, Headers, RequestOptions } from '@angular/http';

import 'rxjs/add/operator/map';

@Injectable()
export class UserService {

	base = "http://localhost:8050/leaderboard"

	constructor(private http: Http) {}


	getUsers(org) {	
		let url =  this.base + "/orgusers/" + org + "?page_size=50"
		let headers    = new Headers({'Content-Type': 'application/json'})
		let options    = new RequestOptions({ headers: headers })			
		return this.http.get(url, options)
			.map(response => response.json())	
	}

	getOrgs(filter) {
		console.log("getting orgs")
		let url =  this.base + "/orgs" + (filter == "" ? "" : "?filter=" + filter)
		let headers    = new Headers({'Content-Type': 'application/json'})
		let options    = new RequestOptions({ headers: headers })			
		return this.http.get(url, options)
			.map(response => response.json())
	}


}