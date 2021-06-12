import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Router} from '@angular/router';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};

@Injectable()
export class HomeServices {
  createUrl = 'https://movie.graved.ch/api/lobby/v1/create-lobby/new-lobby';
  joinUrl = 'http://movie.graved.ch/loby/username+token';

  constructor(private http: HttpClient, private router: Router) {}

  createGame(username: string): void {
    this.http.post(this.createUrl, {username})
      .subscribe(
        (result) => {
          console.log(result);
          /*console.log('ownerID', ownerID);
          console.log('token', token);
          this.router.navigate(['lobby']);*/
        }
      );
  }

  joinGame(username: string, token: string): void {
    this.http.post(this.joinUrl, {username, token}, httpOptions)
      .subscribe(
        ( ) => {
          this.router.navigate(['lobby']);
        }
      );
  }


}
