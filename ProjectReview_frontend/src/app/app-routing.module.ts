import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from './auth.guard';
import { LoginPageComponent } from './login-page/login-page.component';
import { BookingListComponent } from './components/booking-list/booking-list.component';
import { CreateBookingComponent } from './components/create-booking/create-booking.component';
import { UpdateBookingComponent } from './components/update-booking/update-booking.component';
import { VerifyBookingComponent } from './components/verify-booking/verify-booking.component';


const routes: Routes = [
  {path: 'booking-list',component:BookingListComponent,canActivate:[AuthGuard] },
  {path: 'login-page',component:LoginPageComponent },
  {path: '',redirectTo:'login-page',pathMatch:'full'},
  {path: 'create-booking',component:CreateBookingComponent,canActivate:[AuthGuard] },
  {path: 'update-booking/:bookingId/:customerName/:phnNo',component:UpdateBookingComponent,canActivate:[AuthGuard]},
  {path: 'verify-booking/:bookingId/:customerName/:phnNo',component:VerifyBookingComponent  ,canActivate:[AuthGuard]},
  { path: '**', redirectTo: 'login-page' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
