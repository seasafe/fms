import { Injectable } from '@angular/core';
import { throwError, Observable } from 'rxjs';
import { HttpErrorResponse, HttpRequest, HttpClient } from '@angular/common/http';
import { retry, catchError, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class BasehttpService {


  constructor(private http: HttpClient) { }

  post(url: string, object?: any): Observable<any> {
    return this.http.post(url, object).pipe(retry(3), catchError(this.handleError), map((response: Response) => response));
  }

  get(url: string, object?: any): Observable<any> {
    return this.http.get(url, { params: object }).pipe(retry(3), catchError(this.handleError), map((response: Response) => response));
  }

  upload(url: string, formData: any): Observable<any> {
    const req = new HttpRequest('POST', url, formData, {
      reportProgress: true,
      responseType: 'text'
    });
    return this.http.request(req);
  }


  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      console.error('An error occurred:', error.error.message);
    } else {
      console.error(`Backend returned code ${error.status}, ` + `body was: ${error.error}`);
    }
    return throwError('Something bad happened; please try again later.');
  }
}
