import { Auth } from './../auth';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule,RouterModule],
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

        if (role === 'OWNER') {
          this.router.navigate(['/owner']);
        } else {
          this.router.navigate(['/cashier']);
        }
      },
      error: () => {
        alert('Kullanıcı adı veya şifre hatalı');
      }
    });
  }
}
