import { Component, Input} from '@angular/core';
import {HomeServices} from '../services/home.services';

@Component({
  selector: 'app-home',
  providers: [HomeServices],
  templateUrl: 'home.component.html' })

export class HomeComponent {
  public state = 'buttons';
  constructor() { }

  /*addUser(): void {
    this.homeServices.addPlayer();
  }*/
}

