import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

// Components + routage
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home';
import { LobbyComponent } from './lobby';
import { MatchmakingComponent } from './matchmaking';
import { FormulaireCreateComponent } from './home/formulaire-create/formulaire-create.component';
import { FormulaireJoinComponent } from './home/formulaire-join/formulaire-join.component';

// Material ui components
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import { MatGridListModule} from '@angular/material/grid-list';
import {MatListModule} from '@angular/material/list';
import {MatIconModule} from '@angular/material/icon';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import {MatTooltipModule} from '@angular/material/tooltip';
import { MatSliderModule } from '@angular/material/slider';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule} from '@angular/material/card';
import {MatToolbarModule} from '@angular/material/toolbar';

// Setup for server communication
import { HttpClientModule } from '@angular/common/http';
import {MatExpansionModule} from '@angular/material/expansion';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { UserListComponent } from './user-list/user-list.component';
import { UserService } from './services/user.service';
import { HomeServices } from './services/home.services';
import { TokenService } from './services/token.service';
/* import { ChatComponent } from './chat/chat.component';
import {SocketService} from './services/socket.service'; */


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LobbyComponent,
    MatchmakingComponent,
    FormulaireCreateComponent,
    FormulaireJoinComponent,
    UserListComponent,
    /*ChatComponent*/
  ],
    imports: [
        BrowserModule,
        HttpClientModule,
        MatSliderModule,
        MatButtonModule,
        MatCardModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        MatToolbarModule,
        MatFormFieldModule,
        MatInputModule,
        MatGridListModule,
        MatListModule,
        MatIconModule,
        MatProgressBarModule,
        MatTooltipModule,
        MatExpansionModule,
        FormsModule,
        ReactiveFormsModule
    ],
  providers: [
    UserService,
    HomeServices,
    TokenService,
    // SocketService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
