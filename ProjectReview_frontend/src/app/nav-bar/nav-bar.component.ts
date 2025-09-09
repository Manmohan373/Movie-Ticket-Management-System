import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit {



  constructor(private router:Router) { }

  ngOnInit(): void {
  }
  create() {
    this.router.navigate(['create-booking']);
  }
  list() {
      this.router.navigate(['booking-list']);
  }
  logout(){
        this.router.navigate(['login-page']);
  }

}
