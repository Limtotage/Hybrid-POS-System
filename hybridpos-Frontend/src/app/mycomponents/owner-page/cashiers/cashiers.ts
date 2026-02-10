import { Component } from '@angular/core';
import { Auth } from '../../../services/Auth/auth';
import { FormsModule, NgForm } from '@angular/forms';

@Component({
  selector: 'app-cashiers',
  standalone:true,
  imports: [FormsModule],
  templateUrl: './cashiers.html',
  styleUrl: './cashiers.css',
})
export class Cashiers {
  cashier = {
    username: '',
    password: '',
    role: 'CASHIER',
  };
  constructor(private auth: Auth) {}
  createCashier(form: NgForm) {
    this.auth.createCashier(this.cashier).subscribe({
      next: () => {
        alert('Kasiyer başarılıyla Eklendi.');
        form.resetForm();
      },
      error: (err) => {
        alert('Kayıt hatası');
        console.error(err);
      },
    });
  }
}
