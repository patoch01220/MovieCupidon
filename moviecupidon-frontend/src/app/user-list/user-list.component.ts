import { Component, OnDestroy, OnInit } from '@angular/core';
import { User } from '../models/User.model';
import { Subscription } from 'rxjs';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit, OnDestroy {

  users: User[];
  userSubscription: Subscription;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userSubscription = this.userService.userSubject.subscribe(
      (users: User[]) => {
        this.users = users;
    }
    );
    this.userService.emitUsers();
  }
  ngOnDestroy(): void {
    this.userSubscription.unsubscribe();
  }
}
