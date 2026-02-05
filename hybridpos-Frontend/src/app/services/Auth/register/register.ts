import { Auth } from './../auth';
import { Component, NgModule } from '@angular/core';
import { FormsModule, NgModel } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  imports: [FormsModule],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class Register {
  form = {
    username: '',
    password: '',
    role: 'OWNER'
  }
  constructor(
    private auth: Auth,
    private router: Router
  ){}
  register(){
    this.auth.register(this.form).subscribe({
      next: () => {
        alert('Kayıt başarılı');
        this.router.navigate(['/login']);
      },
      error: (err) => {
        alert('Kayıt hatası');
        console.error(err);
      }
    });
  }
}
