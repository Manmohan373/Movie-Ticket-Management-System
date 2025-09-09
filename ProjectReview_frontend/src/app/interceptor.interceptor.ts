import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { Router } from '@angular/router';
import {catchError} from "rxjs/operators";

@Injectable()
export class InterceptorInterceptor implements HttpInterceptor {


  constructor(private router:Router) { }
intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
  const token = localStorage.getItem("access_token");
  const modifiedReq = (req.url!=="http://localhost:9437/TicketBooking/getAccessToken") ? req.clone({
    setHeaders: {
      Authorization: `Bearer ${token}`
    }
  }) : req;
  return next.handle(modifiedReq).pipe(
    catchError(error => {
      if (error.status === 401) {
       this.router.navigateByUrl('login-page');
      }
      return throwError(error);
    })
  );
}
}
