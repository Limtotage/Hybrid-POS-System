import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { forkJoin } from 'rxjs';

import { ReportService } from '../../../services/report/report-service';

import {
  SaleSummary,
  ProductSalesReport,
  TopSellingProduct,
  SupplierReport,
  SaleReport,
  StockMovement,
} from '../../../services/report/report-models';
import { ProductService } from '../../../services/product/product-service';
type ReportPeriod = 'today' | 'week' | 'month' | 'year';
@Component({
  selector: 'app-reports',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './reports.html',
  styleUrl: './reports.css',
})
export class Reports implements OnInit {
  // SUMMARY

  monthSummary!: SaleSummary;

  // PRODUCTS

  monthlyProducts: ProductSalesReport[] = [];
  topSellingProducts: TopSellingProduct[] = [];

  // SUPPLIER

  supplierReports: SupplierReport[] = [];

  // STATE
  showAllTimeTopProducts = false;
  loading = true;
  error = false;
  //Sale
  sales: SaleReport[] = [];

  constructor(
    private reportService: ReportService,
    private cdr: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    this.loadReports();
  }

  selectedPeriod: ReportPeriod = 'month';

  get periodLabel(): string {
    switch (this.selectedPeriod) {
      case 'today':
        return 'Today';
      case 'week':
        return 'This Week';
      case 'year':
        return 'This Year';
      default:
        return 'This Month';
    }
  }

  onPeriodChange(event: Event): void {
    const value = (event.target as HTMLSelectElement).value as ReportPeriod;

    this.selectedPeriod = value;
    this.loadReports();
  }

  loadReports(): void {
    this.loading = true;
    this.error = false;
    this.showAllTimeTopProducts = false;

    let summary$;
    let products$;
    let topProducts$;

    switch (this.selectedPeriod) {
      case 'today':
        summary$ = this.reportService.getTodaySummary();
        products$ = this.reportService.getTodaysProductSales();
        topProducts$ = this.reportService.getTopSellingProductsToday();
        break;
      case 'week':
        summary$ = this.reportService.getWeekSummary();
        products$ = this.reportService.getWeekProductSales();
        topProducts$ = this.reportService.getTopSellingProductsWeek();
        break;

      case 'year':
        summary$ = this.reportService.getYearSummary();
        products$ = this.reportService.getYearlyProductSales();
        topProducts$ = this.reportService.getTopSellingProductsYear();
        break;

      default:
        summary$ = this.reportService.getMonthSummary();
        products$ = this.reportService.getMonthlyProductSales();
        topProducts$ = this.reportService.getTopSellingProductsMonth();
        break;
    }

    forkJoin({
      summary: summary$,
      products: products$,
      topProducts: topProducts$,
      supplierReports: this.reportService.getAllSupplierReports(),
      sales: this.reportService.getAllSales(),
    }).subscribe({
      next: (result) => {
        this.monthSummary = result.summary;
        this.monthlyProducts = result.products;
        this.topSellingProducts = result.topProducts;
        this.supplierReports = result.supplierReports;
        this.sales = result.sales;

        this.loading = false;
        this.cdr.detectChanges();
      },

      error: (error) => {
        console.error('Report verileri alınamadı:', error);

        this.loading = false;
        this.error = true;

        this.cdr.detectChanges();
      },
    });
  }

  toggleAllTimeTopProducts(): void {
    this.showAllTimeTopProducts = !this.showAllTimeTopProducts;

    if (this.showAllTimeTopProducts) {
      this.reportService.getTopSellingProductsAllTime().subscribe({
        next: (products) => {
          this.topSellingProducts = products;
          this.cdr.detectChanges();
        },
        error: (error) => {
          console.error('All time top products alınamadı:', error);
        },
      });
    } else {
      this.loadReports();
    }
  }

  // CALCULATIONS

  get totalProductsSold(): number {
    return this.monthlyProducts.reduce((total, product) => total + product.soldQuantity, 0);
  }

  get cashPercentage(): number {
    if (!this.monthSummary?.totalRevenue) {
      return 0;
    }

    return (this.monthSummary.totalCash / this.monthSummary.totalRevenue) * 100;
  }

  get cardPercentage(): number {
    if (!this.monthSummary?.totalRevenue) {
      return 0;
    }

    return (this.monthSummary.totalCard / this.monthSummary.totalRevenue) * 100;
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
  get totalSuppliedQuantity(): number {
    return this.supplierReports.reduce(
      (total, report) => total + (report.suppliedQuantity || 0),
      0,
    );
  }

  get totalSupplierSoldQuantity(): number {
    return this.supplierReports.reduce((total, report) => total + (report.soldQuantity || 0), 0);
  }

  get totalRemainingQuantity(): number {
    return this.supplierReports.reduce(
      (total, report) => total + (report.remainingQuantity || 0),
      0,
    );
  }
}
