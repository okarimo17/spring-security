import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../environments/environment.development';

@Injectable({
  providedIn: 'root',
})
export class AppService {
  
  private httpClient = inject(HttpClient);
  private readonly apiUrl = environment.server;


  account(){
    return this.httpClient.get(`${this.apiUrl}/account`,  {
      responseType : 'text'
    });
  }

  dashboard(){
    return this.httpClient.get(`${this.apiUrl}/dashboard`, {
      responseType : 'text'
    });
  }

}
