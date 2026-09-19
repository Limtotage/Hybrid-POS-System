import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CashRegisterService } from '../../cashier-page/cash-register/cash-register-service';

@Component({
  selector: 'app-cash-registers',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './cash-registers.html',
  styleUrl: './cash-registers.css',
})
export class CashRegisters implements OnInit {
  cashRegisters: any[] = [];

  cashRegisterName = '';

  showAddModal = false;

  loading = false;

  private readonly baseUrl = 'http://localhost:8080/api/cash-registers';

  constructor(
    private http: HttpClient,
    private cashservice: CashRegisterService,
    private cdr: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    this.getCashRegisters();
  }

  getCashRegisters(): void {
    this.loading = true;
    this.cashservice.getAllCashRegisters().subscribe({
      next: (cashRegisters) => {
        console.log('Kasalar:', cashRegisters);

        this.cashRegisters = cashRegisters;
        this.loading = false;
      },
      error: (err) => {
        console.error('Kasalar alınamadı:', err);
        this.loading = false;
      },
    });
    this.cdr.detach();
  }

  openAddModal(): void {
    this.cashRegisterName = '';
    this.showAddModal = true;
  }

  closeAddModal(): void {
    this.showAddModal = false;
    this.cashRegisterName = '';
  }

  createCashRegister(): void {
    const name = this.cashRegisterName.trim();

    if (!name) {
      alert('Kasa adı boş olamaz.');
      return;
    }

    this.http.post(`${this.baseUrl}?name=${encodeURIComponent(name)}`, {}).subscribe({
      next: () => {
        alert('Kasa başarıyla eklendi.');

        this.closeAddModal();
        this.getCashRegisters();
      },
      error: (err) => {
        console.error('Kasa eklenemedi:', err);
        alert('Kasa eklenemedi.');
      },
    });
  }

  deleteCashRegister(id: number): void {
    const confirmed = confirm('Bu kasayı silmek istediğinize emin misiniz?');

    if (!confirmed) {
      return;
    }

    this.http.delete(`${this.baseUrl}/${id}`).subscribe({
      next: () => {
        alert('Kasa silindi.');
        this.getCashRegisters();
      },
      error: (err) => {
        console.error('Kasa silinemedi:', err);
        alert('Kasa silinemedi.');
      },
    });
  }
}
