import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';

import { Auth } from '../auth';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, RouterModule],
  templateUrl: './login.html',
  styleUrls: ['./login.css'],
})
export class Login {

  loginForm = {
    username: '',
    password: ''
  };

  constructor(
    private auth: Auth,
    private router: Router
  ) {}

  login() {

    this.auth.login(this.loginForm).subscribe({

      next: (res: any) => {

        this.auth.saveToken(res.token);

        const role = this.auth.getRoleFromToken();

        if (role === 'ADMIN') {

          this.router.navigate(['/admin']);

        } else if (role === 'CASHIER') {

          this.router.navigate(['/cashier']);

        } else {

          this.auth.logout();
          alert('Geçersiz kullanıcı rolü.');

        }

      },

      error: () => {
        alert('Kullanıcı adı veya şifre hatalı');
      }

    });
  }
}
