import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';

import { Auth } from '../../services/Auth/auth';
import { ReportService } from '../../services/report/report-service';
import { ProductService } from '../../services/product/product-service';
import { Cashier, CashierService } from '../../services/cashier/cashier-service';
import { SaleReport } from '../../services/report/report-models';

@Component({
  selector: 'app-admin-page',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './admin-page.html',
  styleUrls: ['./admin-page.css'],
})
export class AdminPage implements OnInit {
  totalSales = 0;
  totalProducts = 0;
  totalCashiers = 0;
  lowStockProducts = 0;

  recentSales: SaleReport[] = [];

  loading = false;

  shop = {
    name: '',
    address: '',
  };

  constructor(
    private auth: Auth,
    private router: Router,
    private reportService: ReportService,
    private productService: ProductService,
    private cashierService: CashierService,
    private cdr: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    this.loadDashboard();
  }

  loadDashboard(): void {
    this.loading = true;

    // TODAY'S SALES
    this.reportService.getTodaySummary().subscribe({
      next: (summary) => {
        this.totalSales = summary?.totalRevenue ?? 0;

        this.cdr.detectChanges();
      },

      error: (err) => {
        console.error('Bugünkü satış özeti alınamadı:', err);
      },
    });

    // PRODUCTS
    this.productService.getAllProducts().subscribe({
      next: (products: any[]) => {
        this.totalProducts = products.length;

        this.lowStockProducts = products.filter((product) => product.stockQuantity <= 10).length;

        this.cdr.detectChanges();
      },

      error: (err) => {
        console.error('Ürünler alınamadı:', err);
      },
    });

    // CASHIERS
    this.cashierService.getCashiers().subscribe({
      next: (cashiers: Cashier[]) => {
        this.totalCashiers = cashiers.length;

        this.cdr.detectChanges();
      },

      error: (err) => {
        console.error('Kasiyerler alınamadı:', err);
      },
    });

    // RECENT SALES
    this.reportService.getAllSales().subscribe({
      next: (sales) => {
        this.recentSales = [...sales]
          .sort((a, b) => new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime())
          .slice(0, 5);

        this.loading = false;

        this.cdr.detectChanges();
      },

      error: (err) => {
        console.error('Satışlar alınamadı:', err);

        this.loading = false;

        this.cdr.detectChanges();
      },
    });
  }

  refreshDashboard(): void {
    this.loadDashboard();
  }

  formatCurrency(value: number | null | undefined): string {
    return new Intl.NumberFormat('tr-TR', {
      style: 'currency',
      currency: 'TRY',
    }).format(value ?? 0);
  }

  formatDate(date: string | null | undefined): string {
    if (!date) {
      return '-';
    }

    return new Date(date).toLocaleDateString('tr-TR');
  }

  logout(): void {
    this.auth.logout();
    this.router.navigate(['/login']);
  }
}
