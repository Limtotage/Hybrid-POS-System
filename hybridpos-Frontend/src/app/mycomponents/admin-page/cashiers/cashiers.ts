import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-cashiers',
  standalone: true,
  imports: [
    FormsModule,
    CommonModule
  ],
  templateUrl: './cashiers.html',
  styleUrl: './cashiers.css',
})
export class Cashiers {

  cashier = {
    username: '',
    password: ''
  };
  private readonly baseUrl =
    'http://localhost:8080/api/admin/cashiers';

  constructor(
    private http: HttpClient
  ) {}

  createCashier(form: NgForm): void {

    this.http
      .post(this.baseUrl, this.cashier)
      .subscribe({

        next: () => {

          alert('Kasiyer başarıyla eklendi.');

          form.resetForm();

        },

        error: (err) => {

          alert('Kasiyer eklenemedi.');

          console.error(err);

        }

      });
  }
}
