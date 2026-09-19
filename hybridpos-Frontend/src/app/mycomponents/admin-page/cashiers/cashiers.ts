import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Cashier, CashierRequest, CashierService } from '../../../services/cashier/cashier-service';

@Component({
  selector: 'app-cashiers',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './cashiers.html',
  styleUrl: './cashiers.css',
})
export class Cashiers implements OnInit {
  cashiers: Cashier[] = [];

  showModal = false;
  isEditMode = false;

  selectedCashierId: number | null = null;

  cashier = {
    username: '',
    password: '',
  };

  loading = false;
  saving = false;

  constructor(
    private cashierService: CashierService,
    private cdr: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    this.getCashiers();
  }

  getCashiers(): void {
    this.loading = true;

    this.cashierService.getCashiers().subscribe({
      next: (response) => {
        this.cashiers = response;
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Kasiyerler alınamadı:', err);
        this.loading = false;
        alert('Kasiyerler alınamadı.');
      },
    });
    this.cdr.detectChanges();
  }

  openAddModal(): void {
    this.isEditMode = false;
    this.selectedCashierId = null;

    this.cashier = {
      username: '',
      password: '',
    };

    this.showModal = true;
  }

  openEditModal(cashier: Cashier): void {
    this.isEditMode = true;
    this.selectedCashierId = cashier.id;

    this.cashier = {
      username: cashier.username,
      password: '',
    };

    this.showModal = true;
  }

  closeModal(): void {
    this.showModal = false;
    this.selectedCashierId = null;

    this.cashier = {
      username: '',
      password: '',
    };
  }

  saveCashier(): void {
    const username = this.cashier.username.trim();
    const password = this.cashier.password.trim();

    if (!username) {
      alert('Username boş olamaz.');
      return;
    }

    if (!this.isEditMode && !password) {
      alert('Password boş olamaz.');
      return;
    }

    const data: CashierRequest = {
      username,
    };

    if (password) {
      data.password = password;
    }

    this.saving = true;

    if (this.isEditMode && this.selectedCashierId !== null) {
      this.cashierService.updateCashier(this.selectedCashierId, data).subscribe({
        next: () => {
          alert('Kasiyer başarıyla güncellendi.');
          this.saving = false;
          this.closeModal();
          this.getCashiers();
        },
        error: (err) => {
          console.error('Kasiyer güncellenemedi:', err);
          this.saving = false;
          alert('Kasiyer güncellenemedi.');
        },
      });
    } else {
      this.cashierService
        .createCashier({
          username,
          password,
        })
        .subscribe({
          next: () => {
            alert('Kasiyer başarıyla eklendi.');
            this.saving = false;
            this.closeModal();
            this.getCashiers();
          },
          error: (err) => {
            console.error('Kasiyer eklenemedi:', err);
            this.saving = false;
            alert('Kasiyer eklenemedi.');
          },
        });
    }
  }

  deleteCashier(id: number): void {
    const confirmed = confirm('Bu kasiyeri silmek istediğinize emin misiniz?');

    if (!confirmed) {
      return;
    }

    this.cashierService.deleteCashier(id).subscribe({
      next: () => {
        alert('Kasiyer silindi.');
        this.getCashiers();
      },
      error: (err) => {
        console.error('Kasiyer silinemedi:', err);
        alert('Kasiyer silinemedi.');
      },
    });
  }
}
