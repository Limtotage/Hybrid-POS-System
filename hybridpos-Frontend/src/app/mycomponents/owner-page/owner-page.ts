import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { Auth } from '../../services/Auth/auth';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-owner-page',
  standalone: true,
  imports: [FormsModule,RouterModule],
  templateUrl: './owner-page.html',
  styleUrls: ['./owner-page.css'],
})
export class OwnerPage {
    constructor(
    private auth: Auth,
    private router: Router
  ) {}

  logout() {
    this.auth.logout();
    this.router.navigate(['/login']);
  }
}


