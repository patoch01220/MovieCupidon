import { User } from '../models/User.model';
import {Subject} from 'rxjs';

export class UserService {
  private users: User[] = [];
  userSubject = new Subject<User[]>();

  emitUsers(): void {
  this.userSubject.next(this.users);
  }

  addUser(user: User): void {
    this.users.push(user);
    this.emitUsers();
  }
}
