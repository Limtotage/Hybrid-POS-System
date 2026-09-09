import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { Auth } from '../../services/Auth/auth';
import { FormsModule, NgForm } from '@angular/forms';


@Component({
  selector: 'app-admin-page',
  standalone: true,
  imports: [FormsModule, RouterModule],
  templateUrl: './admin-page.html',
  styleUrls: ['./admin-page.css'],
})
export class AdminPage {
  constructor(
    private auth: Auth,
    private router: Router,
  ) {}

  shop = {
    name: '',
    address: '',
  };
  logout() {
    this.auth.logout();
    this.router.navigate(['/login']);
  }
}
