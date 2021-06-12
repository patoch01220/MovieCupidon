import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { HomeServices } from '../../services/home.services';
import { User } from '../../models/User.model';
import { Token } from '../../models/Token.model';
import { TokenService } from '../../services/token.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-formulaire-join',
  templateUrl: './formulaire-join.component.html'
})
export class FormulaireJoinComponent implements OnInit {

  userTokenForm: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private userService: UserService,
              private homeServices: HomeServices,
              private tokenService: TokenService,
              private router: Router) { }

  ngOnInit(): void {
    this.initForm();
  }

  initForm(): any {
    this.userTokenForm = this.formBuilder.group({
      username: ['', Validators.required],
      owner: false,
      token: ['', Validators.required]
    });
  }

  onSubmitForm(): void {
    const formValue = this.userTokenForm.value;
    const newUser = new User(
      formValue.username,
      formValue.owner
    );
    const newToken = new Token(
      formValue.token
    );
    this.userService.addUser(newUser);
    this.tokenService.emitToken();
    this.router.navigate(['lobby']);
    // this.homeServices.joinGame(newUser.username, newToken.token);
  }

}
