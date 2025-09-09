import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { User } from '../models/user';
import { TicketBookingService } from '../services/ticket-booking.service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {


  user : User = new User();
  userValidation:string=null;
  passwordType : String ="password";


  constructor(private toastr:ToastrService,private bookingService:TicketBookingService,private router:Router) { }

  ngOnInit(): void {
    localStorage.removeItem("access_token");
  }
  change() {
    if(this.passwordType=="text"){
      this.passwordType="password";
    }else{
      this.passwordType="text";
    }
    }

  onSubmit() {
    this.bookingService.getAccessToken(this.user).subscribe(data=>{
      if(data.code == "VALERRCOD"){
        this.userValidation = data.message;
      }
      else if(data.code == "AUTHERRCOD"){
        this.toastr.error(data.message)
        this.user.password = null;
      }
      else{
          localStorage.setItem("access_token",data.details[0].access_token);
          this.toastr.success("Login successful")
          this.router.navigate(['/booking-list']);
      }
    })

  }

 
    removeUserValidation() {     
      this.userValidation=null;
    }

}
