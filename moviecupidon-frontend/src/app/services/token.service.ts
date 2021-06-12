import { Token } from '../models/Token.model';
import {Subject} from 'rxjs';

export class TokenService {
  private token: Token;
  userSubject = new Subject<Token>();

  emitToken(): void {
    this.userSubject.next(this.token);
  }

}
