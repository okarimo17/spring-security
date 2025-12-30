import { Component, inject, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { OAuthService, UserInfo } from 'angular-oauth2-oidc';
import { authConfig } from './config/AuthConfig';
import { AppService } from './app-service';

@Component({
  selector: 'app-root',
  imports: [],
  templateUrl: './app.html'
})
export class App {

  private readonly authService = inject(OAuthService);
  readonly appService = inject(AppService);
  
  

  isAuthInit = signal(false);
  isAuthenticated = signal(false);
  user = signal<ProfileInfo | null>(null);
  apiResult = signal<string|null>('null');

  login(){
    this.authService.initCodeFlow();
    this.user()?.sub
  }


  logout(){
    this.authService.logOut();
  }


  accountApi(){
    this.appService.account().subscribe({
      next: (data) => {
        this.apiResult.set(`Success: ${JSON.stringify(data)}`);
      },
      error: (err) => {
        this.apiResult.set(`Error: ${err.message}`);
      }
    })
  }

  dashboardApi(){
    this.appService.dashboard().subscribe({
      next: (data) => {
        this.apiResult.set(`Success: ${JSON.stringify(data)}`);
      },
      error: (err) => {
        this.apiResult.set(`Error: ${err.message}`);
      }
    })
  }



  constructor(){
    console.log("App component initialized");
    this.configure();

  


  }

  configure() {
    this.authService.configure(authConfig);
    // this.authService.setupAutomaticSilentRefresh();
    this.authService.events.subscribe(e => {
      
      if (e.type === 'token_received' || e.type === 'token_refreshed') {
        this.isAuthenticated.set(true);
        this.loadUserProfile();
      }
      if (e.type === 'session_terminated' || e.type === 'session_error' || e.type === 'user_profile_load_error') {
        this.isAuthenticated.set(false);
        this.user.set(null);
      }

      if (e.type === 'code_error') {
        console.warn('Code error (expected if not logged in):', e);
      }
    });

    this.authService.loadDiscoveryDocumentAndTryLogin().then(() => {
      this.isAuthInit.set(true);

      if (!this.authService.hasValidAccessToken()) {
        this.isAuthenticated.set(false);
      } else {
        this.isAuthenticated.set(true);
        this.loadUserProfile()
  
      }
    });
  }



  private async loadUserProfile() {
    const result = await this.authService.loadUserProfile();
    const profile = (result) as {info :ProfileInfo};
    this.user.set(profile.info);
  }

}
